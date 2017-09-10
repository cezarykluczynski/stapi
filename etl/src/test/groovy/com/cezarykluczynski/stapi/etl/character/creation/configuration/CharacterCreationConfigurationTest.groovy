package com.cezarykluczynski.stapi.etl.character.creation.configuration

import com.cezarykluczynski.stapi.etl.character.common.service.CharactersPageHeadersProvider
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName

class CharacterCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String PAGE_TITLE = 'PAGE_TITLE'

	private CharactersPageHeadersProvider charactersPageHeadersProviderMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private CharacterCreationConfiguration characterCreationConfiguration

	void setup() {
		charactersPageHeadersProviderMock = Mock()
		jobCompletenessDeciderMock = Mock()
		characterCreationConfiguration = new CharacterCreationConfiguration(
				charactersPageHeadersProvider: charactersPageHeadersProviderMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "CharacterReader is created with all pages when step is not completed"() {
		when:
		CharacterReader characterReader = characterCreationConfiguration.characterReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(characterReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CHARACTERS) >> false
		1 * charactersPageHeadersProviderMock.provide() >> createListWithPageHeaderTitle(PAGE_TITLE)
		0 * _
		categoryHeaderTitleList.contains PAGE_TITLE
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
