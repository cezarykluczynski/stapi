package com.cezarykluczynski.stapi.model.common.entity

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import org.apache.commons.lang3.StringUtils
import org.assertj.core.util.Lists
import org.hibernate.annotations.Cache
import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import java.lang.reflect.Field

@SuppressWarnings(['ThrowRuntimeException'])
class EntityCacheableReflectionTest extends AbstractEntityReflectionTest {

	void "all @OneToMany and @ManyToMany relations should have @Cache annotation"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)
		Set<Field> fieldsOneToMany = reflections.getFieldsAnnotatedWith(OneToMany)
		Set<Field> fieldsManyToMany = reflections.getFieldsAnnotatedWith(ManyToMany)

		when:
		entitiesClasses.forEach { entityClass ->
			TrackedEntity trackedEntity = (TrackedEntity) entityClass.getAnnotation(TrackedEntity)
			if (TrackedEntityType.TECHNICAL == trackedEntity.type()) {
				return
			}

			Set<Field> thisFieldsOneToMany = getFieldsByClass(fieldsOneToMany, entityClass)
			Set<Field> thisFieldsManyToMany = getFieldsByClass(fieldsManyToMany, entityClass)
			String entityName = StringUtils.split(entityClass.name, '.').last()

			thisFieldsOneToMany.forEach { it ->
				if (!isFieldAnnotatedWithCache(it)) {
					throw new RuntimeException("There is @OneToMany relation in ${entityName} not marked as @Cache: $it.name")
				}
			}

			thisFieldsManyToMany.forEach { it ->
				if (!isFieldAnnotatedWithCache(it)) {
					throw new RuntimeException("There is @ManyToMany relation in ${entityName} not marked as @Cache: $it.name")
				}
			}
		}

		then:
		notThrown(Exception)
	}

	void "all @Entity classes, except technical entities, are also annotated with @Cache"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)
		Set<Class<?>> cacheClasses = reflections.getTypesAnnotatedWith(Cache)

		when:
		entitiesClasses.forEach { entityClass ->
			TrackedEntity trackedEntity = (TrackedEntity) entityClass.getAnnotation(TrackedEntity)
			if (TrackedEntityType.TECHNICAL == trackedEntity.type()) {
				return
			}

			if (!cacheClasses.contains(entityClass)) {
				throw new RuntimeException("Class $entityClass.name is annotated with @Entity, but not with @Cache")
			}
		}

		then:
		notThrown(Exception)
	}

	private static boolean isFieldAnnotatedWithCache(Field field) {
		Lists.newArrayList(field.annotations).stream().anyMatch { it ->
			it instanceof Cache
		}
	}

}
