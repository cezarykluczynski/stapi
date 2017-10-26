package com.cezarykluczynski.stapi.etl.page.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import info.bliki.api.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PageHeaderProcessor implements ItemProcessor<PageHeader, Page> {

	private final PageApi pageApi;

	public PageHeaderProcessor(PageApi pageApi) {
		this.pageApi = pageApi;
	}

	@Override
	public Page process(PageHeader item) throws Exception {
		Page page = pageApi.getPage(item.getTitle(), item.getMediaWikiSource());

		if (page == null) {
			return null;
		}

		if (page.getPageId() == null) {
			List<PageHeader> redirectPath = page.getRedirectPath();
			if (redirectPath.isEmpty()) {
				page.setPageId(item.getPageId());
			} else {
				page.setPageId(supplementPageId(item));
				page.getRedirectPath().forEach(pageHeader -> pageHeader.setPageId(supplementPageId(pageHeader)));

				if (page.getPageId() == null || page.getRedirectPath().stream().anyMatch(pageHeader -> pageHeader.getPageId() == null)) {
					throw new StapiRuntimeException(String.format("Could not supplement page id for page %s or one of it's redirects", page));
				}
			}
		}

		return page;
	}

	private Long supplementPageId(PageHeader item) {
		if (item.getPageId() != null) {
			return item.getPageId();
		}
		PageInfo pageInfo = pageApi.getPageInfo(item.getTitle(), item.getMediaWikiSource());
		return pageInfo == null ? null : Long.valueOf(pageInfo.getPageid());
	}

}
