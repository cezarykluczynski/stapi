package com.cezarykluczynski.stapi.etl.template.characterbox.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateWithCharacterboxTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class CharacterboxCharacterTemplateEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private PageApi pageApiMock

	private CharacterboxTemplateProcessor characterboxTemplateProcessorMock

	private CharacterTemplateWithCharacterboxTemplateEnrichingProcessor characterTemplateWithCharacterboxTemplateEnrichingProcessorMock

	private CharacterboxCharacterTemplateEnrichingProcessor characterboxCharacterTemplateEnrichingProcessor

	void setup() {
		pageApiMock = Mock()
		characterboxTemplateProcessorMock = Mock()
		characterTemplateWithCharacterboxTemplateEnrichingProcessorMock = Mock()
		characterboxCharacterTemplateEnrichingProcessor = new CharacterboxCharacterTemplateEnrichingProcessor(pageApiMock,
				characterboxTemplateProcessorMock, characterTemplateWithCharacterboxTemplateEnrichingProcessorMock)
	}

	void "adds template part with page title when no template parts are present"() {
		given:
		Template template = new Template()
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new PageEntity(
				title: TITLE
		))

		when:
		characterboxCharacterTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		template.parts.size() == 1
		template.parts[0].value == TITLE

	}

	void "does not interact with other dependencies when PageApi returns null"() {
		given:
		Template template = new Template(title: TemplateTitle.MBETA)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new PageEntity(
				title: TITLE
		))

		when:
		characterboxCharacterTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxCharacterTemplateEnrichingProcessor.SOURCE) >> null
		0 * _
	}

	@SuppressWarnings('BracesForMethod')
	void """when template and page is found, and CharacterboxTemplateProcessor returns null,
			IndividualTemplateWithCharacterboxTemplateEnrichingProcessor is not called"""() {
		given:
		Template template = new Template(title: TemplateTitle.MBETA)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new PageEntity(
				title: TITLE
		))
		Page page = new Page()

		when:
		characterboxCharacterTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxCharacterTemplateEnrichingProcessor.SOURCE) >> page
		1 * characterboxTemplateProcessorMock.process(page) >> null
	}

	@SuppressWarnings('BracesForMethod')
	void """when template and page is found, and CharacterboxTemplateProcessor returns template,
			IndividualTemplateWithCharacterboxTemplateEnrichingProcessor is called"""() {
		given:
		Template template = new Template(title: TemplateTitle.MBETA)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new PageEntity(
				title: TITLE
		))
		Page page = new Page()
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate()

		when:
		characterboxCharacterTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, characterTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxCharacterTemplateEnrichingProcessor.SOURCE) >> page
		1 * characterboxTemplateProcessorMock.process(page) >> characterboxTemplate
		1 * characterTemplateWithCharacterboxTemplateEnrichingProcessorMock
				.enrich(_) >> { EnrichablePair<CharacterboxTemplate, CharacterTemplate> enrichablePair ->
			assert enrichablePair.input == characterboxTemplate
			assert enrichablePair.output == characterTemplate
		}
	}

}
