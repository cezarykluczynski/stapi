package com.cezarykluczynski.stapi.etl.species.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class SpeciesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SPECIES = 'TITLE_SPECIES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SpeciesCreationConfiguration speciesCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		speciesCreationConfiguration = new SpeciesCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SpeciesReader is created with all pages when step is not completed"() {
		when:
		SpeciesReader speciesReader = speciesCreationConfiguration.speciesReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(speciesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.SPECIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SPECIES)
		0 * _
		categoryHeaderTitleList.contains TITLE_SPECIES
	}

	void "SpeciesReader is created with no pages when step is completed"() {
		when:
		SpeciesReader speciesReader = speciesCreationConfiguration.speciesReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(speciesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
