package com.cezarykluczynski.stapi.etl.template.character.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.character.creation.service.CharacterPageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class CharacterTemplatePageProcessorTest extends Specification {

	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String TITLE = 'TITLE'

	private CharacterPageFilter characterPageFilterMock

	private PageBindingService pageBindingServiceMock

	private CharacterTemplateCompositeEnrichingProcessor characterTemplateCompositeEnrichingProcessorMock

	private CharacterTemplatePageProcessor characterTemplatePageProcessor

	void setup() {
		characterPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		characterTemplateCompositeEnrichingProcessorMock = Mock()
		characterTemplatePageProcessor = new CharacterTemplatePageProcessor(characterPageFilterMock, pageBindingServiceMock,
				characterTemplateCompositeEnrichingProcessorMock)
	}

	void "returns null when CharacterPageFilter returns true"() {
		given:
		Page page = new Page()

		when:
		CharacterTemplate characterTemplate = characterTemplatePageProcessor.process(page)

		then:
		1 * characterPageFilterMock.shouldBeFilteredOut(page) >> true
		characterTemplate == null
	}

	void "sets name, bind page, and passed CharacterTemplate stub to CharacterTemplateCompositeEnrichingProcessor"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		PageEntity pageEntity = new PageEntity()

		when:
		CharacterTemplate characterTemplate = characterTemplatePageProcessor.process(page)

		then:
		1 * characterPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * characterTemplateCompositeEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Page, CharacterTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		0 * _
		characterTemplate.name == TITLE
		characterTemplate.page == pageEntity
	}

}
