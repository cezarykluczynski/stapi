package com.cezarykluczynski.stapi.etl.character.link.configuration

import com.cezarykluczynski.stapi.etl.character.common.service.CharactersPageHeadersProvider
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkReader
import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName

class CharacterLinkConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String PAGE_TITLE = 'PAGE_TITLE'

	private CharactersPageHeadersProvider charactersPageHeadersProviderMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private CharacterLinkConfiguration characterLinkConfiguration

	void setup() {
		charactersPageHeadersProviderMock = Mock()
		jobCompletenessDeciderMock = Mock()
		characterLinkConfiguration = new CharacterLinkConfiguration(
				charactersPageHeadersProvider: charactersPageHeadersProviderMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "CharacterLinkReader is created with all pages when step is not completed"() {
		when:
		CharacterLinkReader characterLinkReader = characterLinkConfiguration.characterLinkReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(characterLinkReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.LINK_CHARACTERS) >> false
		1 * charactersPageHeadersProviderMock.provide() >> createListWithPageHeaderTitle(PAGE_TITLE)
		0 * _
		categoryHeaderTitleList.contains PAGE_TITLE
	}

	void "CharacterLinkReader is created with no pages when step is completed"() {
		when:
		CharacterLinkReader characterLinkReader = characterLinkConfiguration.characterLinkReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(characterLinkReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.LINK_CHARACTERS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
