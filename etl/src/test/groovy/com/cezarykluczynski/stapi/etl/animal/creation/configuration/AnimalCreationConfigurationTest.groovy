package com.cezarykluczynski.stapi.etl.animal.creation.configuration

import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class AnimalCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ANIMALS = 'TITLE_ANIMALS'
	private static final String TITLE_EARTH_ANIMALS = 'TITLE_EARTH_ANIMALS'
	private static final String TITLE_AVIANS = 'TITLE_AVIANS'
	private static final String TITLE_CANINES = 'TITLE_CANINES'
	private static final String TITLE_FELINES = 'TITLE_FELINES'

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
		1 * categoryApiMock.getPages(CategoryTitle.ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ANIMALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.EARTH_ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_EARTH_ANIMALS)
		1 * categoryApiMock.getPages(CategoryTitle.AVIANS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_AVIANS)
		1 * categoryApiMock.getPages(CategoryTitle.CANINES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_CANINES)
		1 * categoryApiMock.getPages(CategoryTitle.FELINES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FELINES)
		0 * _
		categoryHeaderTitleList.contains TITLE_ANIMALS
		categoryHeaderTitleList.contains TITLE_EARTH_ANIMALS
		categoryHeaderTitleList.contains TITLE_AVIANS
		categoryHeaderTitleList.contains TITLE_CANINES
		categoryHeaderTitleList.contains TITLE_FELINES
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
