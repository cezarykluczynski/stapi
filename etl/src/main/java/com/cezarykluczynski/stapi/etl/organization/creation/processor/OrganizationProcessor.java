package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class OrganizationProcessor extends CompositeItemProcessor<PageHeader, Organization> {

	public OrganizationProcessor(PageHeaderProcessor pageHeaderProcessor, OrganizationPageProcessor organizationPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, organizationPageProcessor));
	}

}
