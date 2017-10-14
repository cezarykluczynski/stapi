package com.cezarykluczynski.stapi.model.common.entity

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity
import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import spock.lang.Specification

import javax.persistence.Entity

class EntityRepositoryBindingReflectionTest extends Specification {

	void "all entities should have right repositories bound"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)

		expect:
		!entitiesClasses.empty
		entitiesClasses.forEach {
			TrackedEntity trackedEntity = it.getDeclaredAnnotation(TrackedEntity)
			String repositorySimpleName = trackedEntity.repository().simpleName
			String entitySimpleName = it.simpleName
			if (entitySimpleName == 'SimpleStep') {
				return
			}

			assert entitySimpleName + 'Repository' == repositorySimpleName
		}
	}

}
