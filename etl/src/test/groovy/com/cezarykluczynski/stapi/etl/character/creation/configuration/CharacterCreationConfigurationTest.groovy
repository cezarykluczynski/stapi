package com.cezarykluczynski.stapi.etl.character.creation.configuration

import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class CharacterCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_INDIVIDUALS = 'TITLE_INDIVIDUALS'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private CharacterCreationConfiguration characterCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		characterCreationConfiguration = new CharacterCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "CharacterReader is created with all pages when step is not completed"() {
		when:
		CharacterReader characterReader = characterCreationConfiguration.characterReader()
		List<String> categoryHeaderTitleList = readerToList(characterReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CHARACTERS) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.INDIVIDUALS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_INDIVIDUALS)
		0 * _
		categoryHeaderTitleList.contains TITLE_INDIVIDUALS
	}

	void "CharacterReader is created with no pages when step is completed"() {
		when:
		CharacterReader characterReader = characterCreationConfiguration.characterReader()
		List<String> categoryHeaderTitleList = readerToList(characterReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CHARACTERS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
