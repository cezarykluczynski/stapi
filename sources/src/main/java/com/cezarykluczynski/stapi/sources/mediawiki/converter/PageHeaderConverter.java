package com.cezarykluczynski.stapi.sources.mediawiki.converter;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
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

	public PageHeader fromPageInfo(PageInfo pageInfo, MediaWikiSource mediaWikiSource) {
		return PageHeader.builder()
				.pageId(Long.valueOf(pageInfo.getPageid()))
				.title(pageInfo.getTitle())
				.mediaWikiSource(mediaWikiSource)
				.build();
	}

}
