package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageSectionExtractor {

	public PageSection extractLastPageSection(Page page) {
		List<PageSection> pageSectionList = page.getSections();

		if (CollectionUtils.isEmpty(pageSectionList)) {
			return null;
		}

		List<PageSection> pageSectionListInverted = pageSectionList
				.stream()
				.sorted((left, right) -> left.getByteOffset().compareTo(right.getByteOffset()))
				.collect(Collectors.toList());

		return pageSectionListInverted.get(pageSectionListInverted.size() - 1);
	}

	public List<PageSection> findByTitles(Page page, String... titles) {
		Preconditions.checkNotNull(page);
		Preconditions.checkNotNull(titles[0]);
		List<String> titleList = Lists.newArrayList(titles);

		return page.getSections()
				.stream()
				.filter(pageSection -> titleList.contains(pageSection.getText()))
				.collect(Collectors.toList());
	}

}
