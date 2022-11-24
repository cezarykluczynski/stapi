package com.cezarykluczynski.stapi.etl.organization.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class OrganizationCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private PageApi pageApi;

	@Inject
	private PageHeaderConverter pageHeaderConverter;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public OrganizationReader organizationReader() {
		List<PageHeader> organizations = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS)) {
			organizations.addAll(categoryApi.getPages(CategoryTitles.ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			// TODO: rather than getting pages by title, grab all pages that have sidebar_organization template
			// https://memory-alpha.fandom.com/api.php?action=query&prop=transcludedin&tilimit=500&tinamespace=0&titles=Template:Sidebar_organization
			organizations.add(pageHeaderConverter.fromPage(pageApi.getPage(PageTitle.STARFLEET, MediaWikiSource.MEMORY_ALPHA_EN)));
			organizations.add(pageHeaderConverter.fromPage(pageApi.getPage(PageTitle.STARFLEET_MIRROR, MediaWikiSource.MEMORY_ALPHA_EN)));
		}

		return new OrganizationReader(SortingUtil.sortedUnique(organizations));
	}

}
