package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.service.EntityMetadataProvider;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CacheableClassForFullEntitiesSearchProvider {

	// A few entities has priority because of results of performance tests, and the rest is decided base on relations they have.
	private final List<Class> entities = Lists.newArrayList(
			Species.class,
			Movie.class,
			AstronomicalObject.class,
			Character.class,
			Performer.class
	);

	private final EntityMetadataProvider entityMetadataProvider;

	public CacheableClassForFullEntitiesSearchProvider(EntityMetadataProvider entityMetadataProvider) {
		this.entityMetadataProvider = entityMetadataProvider;
		initialize();
	}

	private void initialize() {
		List<Class> entityClasses = entityMetadataProvider.provideClassNameToSymbolMap().keySet().stream().sorted()
				.map(className -> {
					try {
						return Class.forName(className);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());
		for (Class entityClass : entityClasses) {
			if (entities.contains(entityClass)) {
				continue;
			}
			final Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field field : declaredFields) {
				if (Set.class.isAssignableFrom(field.getType())) {
					entities.add(entityClass);
					break;
				}
			}
		}
	}

	public synchronized boolean isClassCacheable(Class entityClass) {
		return entities.contains(entityClass);
	}

	public List<Class> getListOfEntities() {
		return List.copyOf(entities);
	}

}
