package com.cezarykluczynski.stapi.etl.organization.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.SpecialApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class OrganizationCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private PageApi pageApi;

	@Inject
	private SpecialApi specialApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public OrganizationReader organizationReader() {
		List<PageHeader> organizations = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS)) {
			organizations.addAll(categoryApi.getPages(CategoryTitles.ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(specialApi.getPagesTranscludingTemplate(TemplateTitle.SIDEBAR_ORGANIZATION, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new OrganizationReader(SortingUtil.sortedUnique(organizations));
	}

}
