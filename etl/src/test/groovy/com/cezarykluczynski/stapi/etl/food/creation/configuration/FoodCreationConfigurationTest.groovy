package com.cezarykluczynski.stapi.etl.food.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class FoodCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_FOODS = 'TITLE_FOODS'
	private static final String TITLE_BEVERAGES = 'TITLE_BEVERAGES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private FoodCreationConfiguration foodCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		foodCreationConfiguration = new FoodCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "FoodReader is created is created with all pages when step is not completed"() {
		when:
		FoodReader foodReader = foodCreationConfiguration.foodReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(foodReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_FOODS) >> false

		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.FOODS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FOODS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.BEVERAGES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BEVERAGES)
		0 * _
		categoryHeaderTitleList.contains TITLE_FOODS
		categoryHeaderTitleList.contains TITLE_BEVERAGES
	}

	void "FoodReader is created with no pages when step is completed"() {
		when:
		FoodReader foodReader = foodCreationConfiguration.foodReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(foodReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_FOODS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
