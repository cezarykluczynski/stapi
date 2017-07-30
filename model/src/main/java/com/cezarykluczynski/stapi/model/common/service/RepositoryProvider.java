package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RepositoryProvider {

	private final EntityMatadataProvider entityMatadataProvider;

	private final Repositories repositories;

	private Map<Class, CrudRepository> map;

	@Inject
	public RepositoryProvider(EntityMatadataProvider entityMatadataProvider, Repositories repositories) {
		this.entityMatadataProvider = entityMatadataProvider;
		this.repositories = repositories;
	}

	public Map<Class, CrudRepository> provide() {
		if (map == null) {
			synchronized (this) {
				if (map == null) {
					map = doProvide();
				}
			}
		}

		return map;
	}

	private Map<Class, CrudRepository> doProvide() {
		return entityMatadataProvider.provideClassNameToMetadataMap().entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.map(ClassMetadata::getMappedClass)
				.filter(this::isPrimaryEntity)
				.map(clazz -> {
					CrudRepository crudRepository = (CrudRepository) repositories.getRepositoryFor(clazz);
					return Pair.of(clazz, crudRepository);
				})
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
	}

	private boolean isPrimaryEntity(Class clazz) {
		if (!clazz.isAnnotationPresent(TrackedEntity.class)) {
			return false;
		}

		TrackedEntity trackedEntity = (TrackedEntity) clazz.getAnnotation(TrackedEntity.class);
		return TrackedEntityType.FICTIONAL_PRIMARY.equals(trackedEntity.type()) || TrackedEntityType.REAL_WORLD_PRIMARY.equals(trackedEntity.type());
	}

}
