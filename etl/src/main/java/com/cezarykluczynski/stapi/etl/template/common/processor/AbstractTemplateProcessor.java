package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractTemplateProcessor {

	protected Optional<Template> findTemplate(Page page, String name) {
		if (CollectionUtils.isEmpty(page.getTemplates())) {
			return Optional.empty();
		}

		return page.getTemplates().stream()
				.filter(template -> name.equals(template.getTitle()))
				.findFirst();
	}

	protected List<Template> filterByTitle(List<Template> templateList, String... name) {
		List<String> nameList = Lists.newArrayList(name);
		return templateList.stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.collect(Collectors.toList());
	}

	protected com.cezarykluczynski.stapi.model.page.entity.Page fromPageToPageEntity(Page page) {
		return com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(page.getPageId())
				.title(page.getTitle())
				.build();
	}

	protected com.cezarykluczynski.stapi.model.page.entity.Page fromPageHeaderToPageEntity(PageHeader pageHeader) {
		return com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(pageHeader.getPageId())
				.title(pageHeader.getTitle())
				.build();
	}

}
