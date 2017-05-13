package com.cezarykluczynski.stapi.server.common.documentation.service;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.stereotype.Service;

@Service
public class ReflectionReader {

	public Reflections readPackage(String packageName) {
		return new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage(packageName))
				.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new FieldAnnotationsScanner(),
						new MethodParameterNamesScanner())
				.filterInputsBy(new FilterBuilder().includePackage(packageName)));
	}

}
