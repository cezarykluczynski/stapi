package com.cezarykluczynski.stapi.etl.template.service;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateFilter {

	public List<Template> filterByTitle(List<Template> templateList, String... name) {
		List<String> nameList = Lists.newArrayList(name);
		return templateList.stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.collect(Collectors.toList());
	}

}
