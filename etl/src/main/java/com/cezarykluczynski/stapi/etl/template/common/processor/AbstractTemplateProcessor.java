package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractTemplateProcessor {

	protected Optional<Template> findTemplate(Page page, String... names) {
		List<String> nameList = Lists.newArrayList(names);
		if (CollectionUtils.isEmpty(page.getTemplates())) {
			return Optional.empty();
		}

		return page.getTemplates().stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.findFirst();
	}

	protected List<Template> filterByTitle(List<Template> templateList, String... name) {
		List<String> nameList = newArrayList(name);
		return templateList.stream()
				.filter(template -> nameList.contains(template.getTitle()))
				.collect(Collectors.toList());
	}

	protected com.cezarykluczynski.stapi.model.page.entity.Page fromPageToPageEntity(Page page) {
		return com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(page.getPageId())
				.title(page.getTitle())
				.mediaWikiSource(map(page.getMediaWikiSource()))
				.build();
	}

	protected com.cezarykluczynski.stapi.model.page.entity.Page fromPageHeaderToPageEntity(PageHeader pageHeader) {
		return com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(pageHeader.getPageId())
				.title(pageHeader.getTitle())
				.mediaWikiSource(map(pageHeader.getMediaWikiSource()))
				.build();
	}

	protected MediaWikiSource map(com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource mediaWikiSource) {
		// TODO: write separate mapper
		return mediaWikiSource == null ? null : MediaWikiSource.valueOf(mediaWikiSource.name());
	}

}
