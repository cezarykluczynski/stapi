package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine.processor.MagazineTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineProcessor extends CompositeItemProcessor<PageHeader, Magazine> {

	public MagazineProcessor(PageHeaderProcessor pageHeaderProcessor, MagazineTemplatePageProcessor magazineTemplatePageProcessor,
			MagazineTemplateProcessor magazineTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, magazineTemplatePageProcessor, magazineTemplateProcessor));
	}

}
