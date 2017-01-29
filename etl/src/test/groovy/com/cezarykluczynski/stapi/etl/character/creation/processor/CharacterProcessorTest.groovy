package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePageProcessor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class CharacterProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessorMock

	private CharacterIndividualTemplateProcessor characterIndividualTemplateProcessorMock

	private CharacterProcessor characterProcessor

	void setup() {
		pageHeaderProcessorMock = Mock(PageHeaderProcessor)
		individualTemplatePageProcessorMock = Mock(IndividualTemplatePageProcessor)
		characterIndividualTemplateProcessorMock = Mock(CharacterIndividualTemplateProcessor)
		characterProcessor = new CharacterProcessor(pageHeaderProcessorMock, individualTemplatePageProcessorMock,
				characterIndividualTemplateProcessorMock)
	}

	void "converts PageHeader to Character"() {
		given:
		PageHeader pageHeader = PageHeader.builder().build()
		Page page = new Page()
		IndividualTemplate individualTemplate = new IndividualTemplate()
		Character character = new Character()

		when:
		Character outputCharacter = characterProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * individualTemplatePageProcessorMock.process(page) >> individualTemplate
		1 * characterIndividualTemplateProcessorMock.process(individualTemplate) >> character

		then: 'last processor output is returned'
		outputCharacter == character
	}

}
