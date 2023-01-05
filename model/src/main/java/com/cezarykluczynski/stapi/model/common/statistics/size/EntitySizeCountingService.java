package com.cezarykluczynski.stapi.model.common.statistics.size;

import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider;
import com.google.common.collect.Lists;
import jakarta.persistence.EntityManager;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
class EntitySizeCountingService {

	private final RepositoryProvider repositoryProvider;

	private final JpaContext jpaContext;

	EntitySizeCountingService(RepositoryProvider repositoryProvider, JpaContext jpaContext) {
		this.repositoryProvider = repositoryProvider;
		this.jpaContext = jpaContext;
	}

	Map<Class, Long> countEntities() {
		return repositoryProvider.provide().entrySet()
				.stream()
				.map(entry -> Pair.of(entry.getKey(), entry.getValue().count()))
				.collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
	}

	Long countRelations() {
		List<Field> manyToOneFields = Lists.newArrayList();
		List<Field> manyToManyFields = Lists.newArrayList();
		final Map<Class, CrudRepository> repositoryMap = repositoryProvider.provide();
		repositoryMap.entrySet()
				.stream()
				.forEach(entry -> {
					Class entityClass = entry.getKey();
					final List<Field> declaredFields = Arrays.asList(entityClass.getDeclaredFields());
					for (Field declaredField : declaredFields) {
						if (declaredField.isAnnotationPresent(ManyToOne.class)) {
							manyToOneFields.add(declaredField);
						} else if (declaredField.isAnnotationPresent(ManyToMany.class) && declaredField.isAnnotationPresent(JoinTable.class)) {
							ManyToMany manyToMany = declaredField.getDeclaredAnnotation(ManyToMany.class);
							if (StringUtils.isBlank(manyToMany.mappedBy())) {
								manyToManyFields.add(declaredField);
							}
						}
					}
				});

		AtomicLong manyToOneCount = new AtomicLong();
		manyToOneFields.forEach(field -> {
			Class declaringClass = field.getDeclaringClass();
			final EntityManager entityManager = jpaContext.getEntityManagerByManagedType(declaringClass);
			final Query namedQuery = entityManager.createQuery(String.format("select count(*) from %s where %s is not null",
					declaringClass.getSimpleName(), field.getName()));
			final Long count = (Long) namedQuery.getSingleResult();
			manyToOneCount.addAndGet(count);
		});

		AtomicLong manyToManyCount = new AtomicLong();
		manyToManyFields.forEach(field -> {
			Class declaringClass = field.getDeclaringClass();
			final EntityManager entityManager = jpaContext.getEntityManagerByManagedType(declaringClass);
			JoinTable joinTable = field.getDeclaredAnnotation(JoinTable.class);
			final Optional<JoinColumn> joinColumnOptional = Arrays.stream(joinTable.joinColumns()).findFirst();
			final Optional<JoinColumn> inverseJoinColumnOptional = Arrays.stream(joinTable.inverseJoinColumns()).findFirst();
			if (joinColumnOptional.isPresent() && inverseJoinColumnOptional.isPresent()) {
				final Query nativeQuery = entityManager.createNativeQuery(String.format("select count(*) from %s.%s", "stapi", joinTable.name()));
				final long count = (long) nativeQuery.getSingleResult();
				manyToManyCount.addAndGet(count);
			}
		});

		return manyToOneCount.get() + manyToManyCount.get();
	}

}
