package com.cezarykluczynski.stapi.sources.mediawiki.converter;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.MemoryAlpha;
import info.bliki.api.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageHeaderConverter {

	public List<PageHeader> fromPageInfoList(List<PageInfo> pageInfoList, MediaWikiSource mediaWikiSource) {
		return pageInfoList.stream()
				.filter(pageInfo -> pageInfo.getNs().equals(MemoryAlpha.CONTENT_NAMESPACE))
				.map(pageInfo -> fromPageInfo(pageInfo, mediaWikiSource))
				.collect(Collectors.toList());
	}


	public List<PageHeader> fromPageList(List<Page> pageList) {
		return pageList.stream()
				.map(this::fromPage)
				.collect(Collectors.toList());
	}

	private PageHeader fromPage(Page page) {
		PageHeader pageHeader = new PageHeader();
		pageHeader.setTitle(page.getTitle());
		pageHeader.setPageId(page.getPageId());
		pageHeader.setMediaWikiSource(page.getMediaWikiSource());
		return pageHeader;
	}

	private PageHeader fromPageInfo(PageInfo pageInfo, MediaWikiSource mediaWikiSource) {
		PageHeader pageHeader = new PageHeader();
		pageHeader.setTitle(pageInfo.getTitle());
		pageHeader.setPageId(Long.valueOf(pageInfo.getPageid()));
		pageHeader.setMediaWikiSource(mediaWikiSource);
		return pageHeader;
	}

}
