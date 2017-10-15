package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateTitlesExtractingProcessor {

	public List<String> process(List<Template> item) {
		List<Template> templateList = item == null ? Lists.newArrayList() : item;

		return templateList.stream()
				.map(Template::getTitle)
				.collect(Collectors.toList());
	}

}
