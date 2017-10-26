package com.cezarykluczynski.stapi.etl.company.creation.processor;


import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CompanyProcessor extends CompositeItemProcessor<PageHeader, Company> {

	public CompanyProcessor(PageHeaderProcessor pageHeaderProcessor, CompanyPageProcessor companyPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, companyPageProcessor));
	}

}
