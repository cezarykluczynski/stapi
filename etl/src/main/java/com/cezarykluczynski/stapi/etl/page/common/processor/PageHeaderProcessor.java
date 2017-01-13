package com.cezarykluczynski.stapi.etl.page.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import info.bliki.api.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class PageHeaderProcessor implements ItemProcessor<PageHeader, Page> {

	private PageApi pageApi;

	@Inject
	public PageHeaderProcessor(PageApi pageApi) {
		this.pageApi = pageApi;
	}

	@Override
	public Page process(PageHeader item) throws Exception {
		Page page = pageApi.getPage(item.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);

		if (page.getPageId() == null) {
			if (page.getRedirectPath().isEmpty()) {
				page.setPageId(item.getPageId());
			} else {
				PageInfo pageInfo = pageApi.getPageInfo(page.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
				page.setPageId(Long.valueOf(pageInfo.getPageid()));
			}
		}

		return page;
	}

}
