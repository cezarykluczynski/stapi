package com.cezarykluczynski.stapi.etl.page.common.processor;

import com.cezarykluczynski.stapi.wiki.api.PageApi;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PageHeaderProcessor implements ItemProcessor<PageHeader, Page> {

	private PageApi pageApi;

	@Inject
	public PageHeaderProcessor(PageApi pageApi) {
		this.pageApi = pageApi;
	}

	@Override
	public Page process(PageHeader item) throws Exception {
		return pageApi.getPage(item.getTitle());
	}

}
