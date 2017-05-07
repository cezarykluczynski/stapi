package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
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

	@Inject
	public RepositoryProvider(EntityMatadataProvider entityMatadataProvider, Repositories repositories) {
		this.entityMatadataProvider = entityMatadataProvider;
		this.repositories = repositories;
	}

	public Map<Class<? extends PageAwareEntity>, CrudRepository> provide() {
		return entityMatadataProvider.provideClassNameToMetadataMap().entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.map(ClassMetadata::getMappedClass)
				.filter(PageAwareEntity.class::isAssignableFrom)
				.map(clazz -> (Class<? extends PageAwareEntity>) clazz)
				.map(clazz -> {
					CrudRepository crudRepository = (CrudRepository) repositories.getRepositoryFor(clazz);
					return Pair.of(clazz, crudRepository);
				})
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
	}

}
