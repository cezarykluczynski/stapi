package com.cezarykluczynski.stapi.etl.template.comic_strip.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.character.WikitextSectionsCharactersProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class ComicStripTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_COMIC_STRIP = 'TITLE (comic strip)'

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessorMock

	private WikitextSectionsCharactersProcessor comicStripTemplateCharactersEnrichingProcessorMock

	private ComicStripTemplatePageProcessor comicStripTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		comicStripTemplatePartsEnrichingProcessorMock = Mock()
		comicStripTemplateCharactersEnrichingProcessorMock = Mock()
		comicStripTemplatePageProcessor = new ComicStripTemplatePageProcessor(templateFinderMock, pageBindingServiceMock,
				comicStripTemplatePartsEnrichingProcessorMock, comicStripTemplateCharactersEnrichingProcessorMock)
	}

	void "returns null when sidebar comic strip template is not found in the page"() {
		given:
		Page page = new Page()

		when:
		ComicStripTemplate comicStripTemplate = comicStripTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		0 * _
		comicStripTemplate == null
	}

	void "clears title when it contains '(comic strip)'"() {
		given:
		Page page = new Page(title: TITLE_COMIC_STRIP)
		Template.Part templatePart = Mock()
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		ModelPage modelPage = new ModelPage()

		when:
		ComicStripTemplate comicStripTemplate = comicStripTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.of(template)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicStripTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * comicStripTemplateCharactersEnrichingProcessorMock.process(page) >> Sets.newHashSet()
		0 * _
		comicStripTemplate.title == TITLE
	}

	void "interacts with dependencies when sidebar comic strip template is found"() {
		given:
		Page page = new Page(title: TITLE)
		Template.Part templatePart = Mock()
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		ModelPage modelPage = new ModelPage()
		Character character = Mock()

		when:
		ComicStripTemplate comicStripTemplate = comicStripTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.of(template)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicStripTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, ComicStripTemplate> enrichablePair ->
			assert enrichablePair.input[0] == templatePart
			assert enrichablePair.output != null
		}
		1 * comicStripTemplateCharactersEnrichingProcessorMock.process(page) >> Sets.newHashSet(character)
		0 * _
		comicStripTemplate.title == TITLE
		comicStripTemplate.page == modelPage
		comicStripTemplate.characters.contains character
	}

}
