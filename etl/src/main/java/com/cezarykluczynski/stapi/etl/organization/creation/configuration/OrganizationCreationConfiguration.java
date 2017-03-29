package com.cezarykluczynski.stapi.etl.organization.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class OrganizationCreationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public OrganizationReader organizationReader() {
		List<PageHeader> organizations = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS)) {
			organizations.addAll(categoryApi.getPages(CategoryTitle.AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.BAJORAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.CARDASSIAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.EARTH_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.FEDERATION_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.FERENGI_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.KLINGON_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.LAW_ENFORCEMENT_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.PRISONS_AND_PENAL_COLONIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.ROMULAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.VULCAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.EARTH_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.EARTH_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.EARTH_INTERGOVERNMENTAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.EARTH_MILITARY_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.GOVERNMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.INTERGOVERNMENTAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.RESEARCH_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.SPORTS_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.WARDS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.DS9_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.MILITARY_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			organizations.addAll(categoryApi.getPages(CategoryTitle.MILITARY_UNITS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new OrganizationReader(Lists.newArrayList(Sets.newHashSet(organizations)));
	}

}
