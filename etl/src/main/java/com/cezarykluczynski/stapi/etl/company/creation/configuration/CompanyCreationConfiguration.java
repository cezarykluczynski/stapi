package com.cezarykluczynski.stapi.etl.company.creation.configuration;

import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class CompanyCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public CompanyReader companyReader() {
		List<PageHeader> companies = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMPANIES)) {
			companies.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitles.COMPANIES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new CompanyReader(SortingUtil.sortedUnique(companies));
	}

}
