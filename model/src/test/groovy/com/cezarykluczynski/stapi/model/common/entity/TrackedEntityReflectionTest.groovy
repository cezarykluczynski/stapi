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

@SuppressWarnings('ThrowRuntimeException')
class TrackedEntityReflectionTest extends Specification {

	void "all @Entity classes, are also annotated with @TrackedEntity"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)
		Set<Class<?>> cacheClasses = reflections.getTypesAnnotatedWith(TrackedEntity)

		when:
		entitiesClasses.forEach { it ->
			if (!cacheClasses.contains(it)) {
				throw new RuntimeException("Class $it.name is annotated with @Entity, but not with @TrackedEntity")
			}
		}

		then:
		notThrown(Exception)
	}

}
