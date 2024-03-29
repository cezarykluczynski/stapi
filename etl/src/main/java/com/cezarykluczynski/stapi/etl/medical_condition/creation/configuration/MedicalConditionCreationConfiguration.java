package com.cezarykluczynski.stapi.etl.medical_condition.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class MedicalConditionCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public MedicalConditionReader medicalConditionReader() {
		List<PageHeader> medicalConditions = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MEDICAL_CONDITIONS)) {
			medicalConditions.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_CONDITIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			medicalConditions.addAll(categoryApi.getPages(CategoryTitle.PSYCHOLOGICAL_CONDITIONS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new MedicalConditionReader(SortingUtil.sortedUnique(medicalConditions));
	}

}
