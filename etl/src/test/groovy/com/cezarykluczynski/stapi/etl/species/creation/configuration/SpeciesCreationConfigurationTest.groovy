package com.cezarykluczynski.stapi.etl.species.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class SpeciesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SPECIES = 'TITLE_SPECIES'
	private static final String TITLE_UNNAMED_SPECIES = 'TITLE_UNNAMED_SPECIES'
	private static final String TITLE_NON_CORPOREALS = 'TITLE_NON_CORPOREALS'

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
		1 * categoryApiMock.getPages(CategoryTitle.SPECIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SPECIES)
		1 * categoryApiMock.getPages(CategoryTitle.UNNAMED_SPECIES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_UNNAMED_SPECIES)
		1 * categoryApiMock.getPages(CategoryTitle.NON_CORPOREALS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_NON_CORPOREALS)
		0 * _
		categoryHeaderTitleList.contains TITLE_SPECIES
		categoryHeaderTitleList.contains TITLE_UNNAMED_SPECIES
		categoryHeaderTitleList.contains TITLE_NON_CORPOREALS
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
