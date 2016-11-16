package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractTemplateProcessor {

	// TODO: move from here
	protected Optional<Template> findTemplate(Page page, String... names) {
		List<String> nameList = Lists.newArrayList(names);
		if (CollectionUtils.isEmpty(page.getTemplates())) {
			return Optional.empty();
		}

		return page.getTemplates().stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.findFirst();
	}

	// TODO: move from here
	protected List<Template> filterByTitle(List<Template> templateList, String... name) {
		List<String> nameList = newArrayList(name);
		return templateList.stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.collect(Collectors.toList());
	}

}
