package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplatePageProcessor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class CharacterProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private CharacterTemplatePageProcessor characterTemplatePageProcessorMock

	private CharacterTemplateProcessor characterTemplateProcessorMock

	private CharacterProcessor characterProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		characterTemplatePageProcessorMock = Mock()
		characterTemplateProcessorMock = Mock()
		characterProcessor = new CharacterProcessor(pageHeaderProcessorMock, characterTemplatePageProcessorMock, characterTemplateProcessorMock)
	}

	void "converts PageHeader to Character"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		CharacterTemplate characterTemplate = new CharacterTemplate()
		Character character = new Character()

		when:
		Character outputCharacter = characterProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * characterTemplatePageProcessorMock.process(page) >> characterTemplate

		and:
		1 * characterTemplateProcessorMock.process(characterTemplate) >> character

		then: 'last processor output is returned'
		outputCharacter == character
	}

}
