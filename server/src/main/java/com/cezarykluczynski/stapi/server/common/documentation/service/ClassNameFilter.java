package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassNameFilter {

	Set<Class> getClassesEndingWith(Set<String> fullClassNames, List<String> suffixList) {
		return fullClassNames.stream()
				.filter(className -> StringUtil.endsWithAny(className, suffixList))
				.map(className -> {
					try {
						return Class.forName(className);
					} catch (ClassNotFoundException e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

}
