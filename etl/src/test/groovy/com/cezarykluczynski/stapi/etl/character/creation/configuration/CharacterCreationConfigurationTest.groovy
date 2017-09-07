package com.cezarykluczynski.stapi.etl.character.creation.configuration

import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class CharacterCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_INDIVIDUALS = 'TITLE_INDIVIDUALS'
	private static final String TITLE_MILITARY_PERSONNEL = 'TITLE_MILITARY_PERSONNEL'
	private static final String TITLE_Q_CONTINUUM = 'TITLE_Q_CONTINUUM'
	private static final String TITLE_STARFLEET_PERSONNEL = 'TITLE_STARFLEET_PERSONNEL'
	private static final String TITLE_HOLOGRAMS = 'TITLE_HOLOGRAMS'
	private static final String TITLE_HOLOGRAPHIC_DUPLICATES = 'TITLE_HOLOGRAPHIC_DUPLICATES'
	private static final String TITLE_FICTIONAL_CHARACTERS = 'TITLE_FICTIONAL_CHARACTERS'
	private static final String TITLE_THE_DIXON_HILL_SERIES_CHARACTERS = 'TITLE_THE_DIXON_HILL_SERIES_CHARACTERS'
	private static final String TITLE_SHAKESPEARE_CHARACTERS = 'TITLE_SHAKESPEARE_CHARACTERS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private CharacterCreationConfiguration characterCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		characterCreationConfiguration = new CharacterCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "CharacterReader is created with all pages when step is not completed"() {
		when:
		CharacterReader characterReader = characterCreationConfiguration.characterReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(characterReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CHARACTERS) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.INDIVIDUALS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_MILITARY_PERSONNEL)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_Q_CONTINUUM)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_STARFLEET_PERSONNEL)
		1 * categoryApiMock.getPages(CategoryTitle.HOLOGRAMS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOLOGRAMS)
		1 * categoryApiMock.getPages(CategoryTitle.HOLOGRAPHIC_DUPLICATES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_HOLOGRAPHIC_DUPLICATES)
		1 * categoryApiMock.getPages(CategoryTitle.FICTIONAL_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_FICTIONAL_CHARACTERS)
		1 * categoryApiMock.getPages(CategoryTitle.THE_DIXON_HILL_SERIES_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_THE_DIXON_HILL_SERIES_CHARACTERS)
		1 * categoryApiMock.getPages(CategoryTitle.SHAKESPEARE_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SHAKESPEARE_CHARACTERS)

		0 * _
		categoryHeaderTitleList.contains TITLE_INDIVIDUALS
		categoryHeaderTitleList.contains TITLE_MILITARY_PERSONNEL
		categoryHeaderTitleList.contains TITLE_Q_CONTINUUM
		categoryHeaderTitleList.contains TITLE_STARFLEET_PERSONNEL
		categoryHeaderTitleList.contains TITLE_HOLOGRAMS
		categoryHeaderTitleList.contains TITLE_HOLOGRAPHIC_DUPLICATES
		categoryHeaderTitleList.contains TITLE_FICTIONAL_CHARACTERS
		categoryHeaderTitleList.contains TITLE_THE_DIXON_HILL_SERIES_CHARACTERS
		categoryHeaderTitleList.contains TITLE_SHAKESPEARE_CHARACTERS
	}

	void "CharacterReader is created with no pages when step is completed"() {
		when:
		CharacterReader characterReader = characterCreationConfiguration.characterReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(characterReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CHARACTERS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
