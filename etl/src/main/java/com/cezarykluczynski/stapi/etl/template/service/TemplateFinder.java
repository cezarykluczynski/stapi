package com.cezarykluczynski.stapi.etl.template.service;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemplateFinder {

	public Optional<Template> findTemplate(Page page, String... names) {
		List<String> nameList = Lists.newArrayList(names);
		if (CollectionUtils.isEmpty(page.getTemplates())) {
			return Optional.empty();
		}

		return page.getTemplates().stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.findFirst();
	}

	public List<Template> findTemplates(Page page, String... names) {
		List<String> nameList = Lists.newArrayList(names);
		if (CollectionUtils.isEmpty(page.getTemplates())) {
			return Lists.newArrayList();
		}

		return page.getTemplates().stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.collect(Collectors.toList());
	}

	public boolean hasTemplate(Page page, String name) {
		return findTemplate(page, name).isPresent();
	}

}
