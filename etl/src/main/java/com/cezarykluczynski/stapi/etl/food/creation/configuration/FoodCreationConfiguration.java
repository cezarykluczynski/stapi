package com.cezarykluczynski.stapi.etl.food.creation.configuration;


import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class FoodCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public FoodReader foodReader() {
		List<PageHeader> foods = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_FOODS)) {
			foods.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.FOODS, MediaWikiSource.MEMORY_ALPHA_EN));
			foods.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.BEVERAGES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new FoodReader(Lists.newArrayList(Sets.newHashSet(foods)));
	}

}
