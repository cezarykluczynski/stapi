package com.cezarykluczynski.stapi.etl.template.magazine.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MagazineTemplatePageProcessor implements ItemProcessor<Page, MagazineTemplate> {

	private final PageBindingService pageBindingService;

	@Inject
	public MagazineTemplatePageProcessor(PageBindingService pageBindingService) {
		this.pageBindingService = pageBindingService;
	}

	@Override
	public MagazineTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
