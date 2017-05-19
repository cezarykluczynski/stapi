package com.cezarykluczynski.stapi.model.common.entity

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.expr.ArrayInitializerExpr
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.StringLiteralExpr
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

import java.lang.reflect.Field
import java.util.stream.Collectors

@SuppressWarnings('ClosureAsLastMethodParameter')
abstract class AbstractEntityReflectionTest extends Specification {

	protected static Set<String> extractExcludes(NodeList<MemberValuePair> memberValuePairs) {
		Set<String> excludeSet = Sets.newHashSet()

		memberValuePairs.stream()
				.filter({ it.name.toString() == 'exclude' })
				.forEach {
					if (it.value instanceof ArrayInitializerExpr) {
						((ArrayInitializerExpr) it.value).values.forEach {
							excludeSet.add(((StringLiteralExpr) it).value)
						}
					} else if (it.value instanceof StringLiteralExpr) {
						excludeSet.add(((StringLiteralExpr) it.value).value)
					}
				}

		excludeSet
	}

	protected static Set<Field> getFieldsByClass(Set<Field> fieldSet, Class<?> clazz) {
		fieldSet.stream()
				.filter { field -> field.clazz.name == clazz.name }
				.collect(Collectors.toSet())
	}

	protected Optional<File> getEntityFileOptional(String name) {
		String fileName = name + '.java'
		List<File> files = Lists.newArrayList()
		fillFileList('./model/src/main/java/com/cezarykluczynski/stapi/model', files)
		fillFileList('./src/main/java/com/cezarykluczynski/stapi/model', files)
		files.stream()
				.filter {
					it.path.endsWith("entity${File.separator}${fileName}")
				}
				.findFirst()
	}

	protected static NormalAnnotationExpr getAnnotation(NodeList annotationExprNodeList, String name) {
		annotationExprNodeList.stream()
				.filter { it ->
					if (it instanceof NormalAnnotationExpr) {
						return ((NormalAnnotationExpr) it).name.toString() == name
					}

					false
				}
				.findFirst().orElse(null)
	}

	void fillFileList(String directoryName, List<File> files) {
		File directory = new File(directoryName)

		File[] fileList = directory.listFiles()
		for (File file : fileList) {
			if (file.isFile()) {
				files.add(file)
			} else if (file.isDirectory()) {
				fillFileList(file.absolutePath, files)
			}
		}
	}

}
