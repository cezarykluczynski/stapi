package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RepositoryProvider {

	private final EntityMetadataProvider entityMetadataProvider;

	private final Repositories repositories;

	private Map<Class, CrudRepository> map;

	public RepositoryProvider(EntityMetadataProvider entityMetadataProvider, Repositories repositories) {
		this.entityMetadataProvider = entityMetadataProvider;
		this.repositories = repositories;
	}

	public synchronized Map<Class, CrudRepository> provide() {
		if (map == null) {
			map = doProvide();
		}

		return map;
	}

	private Map<Class, CrudRepository> doProvide() {
		return entityMetadataProvider.provideClassNameToMetadataMap().entrySet()
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
		TrackedEntityType trackedEntityType = trackedEntity.type();
		return TrackedEntityType.FICTIONAL_PRIMARY.equals(trackedEntityType) || TrackedEntityType.REAL_WORLD_PRIMARY.equals(trackedEntityType);
	}

}
