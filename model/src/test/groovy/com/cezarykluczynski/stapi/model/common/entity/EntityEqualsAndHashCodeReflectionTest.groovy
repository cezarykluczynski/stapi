package com.cezarykluczynski.stapi.model.common.entity

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.google.common.collect.Sets
import org.apache.commons.lang3.StringUtils
import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import java.lang.reflect.Field
import java.util.stream.Collectors

@SuppressWarnings('ThrowRuntimeException')
class EntityEqualsAndHashCodeReflectionTest extends AbstractEntityReflectionTest {

	void "all entities should have only simple fields in their equals and hashCode implementations"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.model'))
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		Set<Class<?>> entitiesClasses = reflections.getTypesAnnotatedWith(Entity)
		Set<Field> fieldsOneToOne = reflections.getFieldsAnnotatedWith(OneToOne)
		Set<Field> fieldsOneToMany = reflections.getFieldsAnnotatedWith(OneToMany)
		Set<Field> fieldsManyToMany = reflections.getFieldsAnnotatedWith(ManyToMany)

		when:
		entitiesClasses.forEach { entityClass ->
			if (entityClass.name.endsWith('SimpleStep')) {
				return
			}

			Set<Field> thisFieldsOneToOne = getFieldsByClass(fieldsOneToOne, entityClass)
			Set<Field> thisFieldsOneToMany = getFieldsByClass(fieldsOneToMany, entityClass)
			Set<Field> thisFieldsManyToMany = getFieldsByClass(fieldsManyToMany, entityClass)
			Set<Field> allRelationFields = Sets.newHashSet()
			allRelationFields.addAll(thisFieldsOneToOne)
			allRelationFields.addAll(thisFieldsOneToMany)
			allRelationFields.addAll(thisFieldsManyToMany)
			Set<String> allFieldNames = allRelationFields.stream()
					.map { it.name }
					.collect(Collectors.toSet())
			String entityName = StringUtils.split(entityClass.name, '.').last()
			Optional<File> entityFileOptional = getEntityFileOptional(entityName)
			if (!entityFileOptional.isPresent()) {
				throw new RuntimeException("Missing entity file: ${entityName}")
			}
			CompilationUnit compilationUnit = JavaParser.parse(entityFileOptional.get())
			NodeList annotations = compilationUnit.types.annotations
			NormalAnnotationExpr toStringAnnotation = getAnnotation(annotations, 'ToString')
			NormalAnnotationExpr equalsAndHashCodeAnnotation = getAnnotation(annotations, 'EqualsAndHashCode')

			Set<String> toStringExcludes = toStringAnnotation == null ? Sets.newHashSet() : extractExcludes(toStringAnnotation.pairs)
			Set<String> equalsAndHashCodeExcludes = equalsAndHashCodeAnnotation == null ? Sets.newHashSet()
					: extractExcludes(equalsAndHashCodeAnnotation.pairs)

			Sets.SetView<String> toStringDifference = Sets.difference(allFieldNames, toStringExcludes)
			Sets.SetView<String> equalsAndHashCodeDifference = Sets.difference(allFieldNames, equalsAndHashCodeExcludes)

			if (!toStringDifference.toSet().empty) {
				throw new RuntimeException("There are relations in ${entityName} not excluded from @ToString annotation: ${toStringDifference}")
			}

			if (!equalsAndHashCodeDifference.toSet().empty) {
				throw new RuntimeException("There are relations in ${entityName} not excluded from @EqualsAndHashCode annotation: " +
						"${equalsAndHashCodeDifference}")
			}
		}

		then:
		notThrown(Exception)
	}

}
