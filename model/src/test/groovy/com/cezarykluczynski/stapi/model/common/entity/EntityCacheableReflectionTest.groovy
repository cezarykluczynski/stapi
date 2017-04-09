package com.cezarykluczynski.stapi.model.common.entity

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

@SuppressWarnings(['ThrowRuntimeException', 'ClosureAsLastMethodParameter'])
class EntityCacheableReflectionTest extends AbstractEntityReflectionTest {

	void "all @OneToMany and @ManyToMany relations should have @Cacheable annotation"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)
		Set<Field> fieldsOneToMany = reflections.getFieldsAnnotatedWith(OneToMany)
		Set<Field> fieldsManyToMany = reflections.getFieldsAnnotatedWith(ManyToMany)

		when:
		entitiesClasses.forEach({ entityClass ->
			Set<Field> thisFieldsOneToMany = getFieldsByClass(fieldsOneToMany, entityClass)
			Set<Field> thisFieldsManyToMany = getFieldsByClass(fieldsManyToMany, entityClass)
			String entityName = StringUtils.split(entityClass.name, '.').last()

			thisFieldsOneToMany.forEach({ it ->
				if (!isFieldAnnotatedWithCache(it)) {
					throw new RuntimeException("There is @OneToMany relation in ${entityName} not marked as @Cacheable: $it.name")
				}
			})

			thisFieldsManyToMany.forEach({ it ->
				if (!isFieldAnnotatedWithCache(it)) {
					throw new RuntimeException("There is @ManyToMany relation in ${entityName} not marked as @Cacheable: $it.name")
				}
			})
		})

		then:
		notThrown(Exception)
	}

	private static boolean isFieldAnnotatedWithCache(Field field) {
		Lists.newArrayList(field.annotations).stream().anyMatch({ it ->
			it instanceof Cache
		})
	}

}
