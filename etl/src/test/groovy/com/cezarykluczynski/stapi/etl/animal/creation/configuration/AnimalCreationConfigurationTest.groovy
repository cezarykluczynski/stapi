package com.cezarykluczynski.stapi.etl.animal.creation.configuration

import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class AnimalCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ANIMALS = 'TITLE_ANIMALS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private AnimalCreationConfiguration animalCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		animalCreationConfiguration = new AnimalCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "AnimalReader is created is created with all pages when step is not completed"() {
		when:
		AnimalReader animalReader = animalCreationConfiguration.animalReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(animalReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ANIMALS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ANIMALS)
		0 * _
		categoryHeaderTitleList.contains TITLE_ANIMALS
	}

	void "AnimalReader is created with no pages when step is completed"() {
		when:
		AnimalReader animalReader = animalCreationConfiguration.animalReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(animalReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ANIMALS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
