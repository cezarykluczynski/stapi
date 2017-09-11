package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.book.creation.service.BookPageFilter
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.character.WikitextSectionsCharactersProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class BookTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private BookPageFilter bookPageFilterMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private CategoriesBookTemplateEnrichingProcessor categoriesBookTemplateEnrichingProcessorMock

	private BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessorMock

	private WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessorMock

	private BookTemplatePageProcessor bookTemplatePageProcessor

	void setup() {
		bookPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		categoriesBookTemplateEnrichingProcessorMock = Mock()
		bookTemplatePartsEnrichingProcessorMock = Mock()
		wikitextSectionsCharactersProcessorMock = Mock()
		bookTemplatePageProcessor = new BookTemplatePageProcessor(bookPageFilterMock, pageBindingServiceMock, templateFinderMock,
				categoriesBookTemplateEnrichingProcessorMock, bookTemplatePartsEnrichingProcessorMock, wikitextSectionsCharactersProcessorMock)
	}

	void "returns null when BookPageFilter returns true"() {
		given:
		Page page = Mock()

		when:
		BookTemplate bookTemplate = bookTemplatePageProcessor.process(page)

		then:
		1 * bookPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		bookTemplate == null
	}

	void "clean title when it contains blacklisted suffix"() {
		given:
		Page page = new Page(title: TITLE + ' ' + RandomUtil.randomItem(BookTemplatePageProcessor.TITLE_PART_LIST_TO_CLEAR))

		when:
		BookTemplate bookTemplate = bookTemplatePageProcessor.process(page)

		then:
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * templateFinderMock.findTemplate(*_) >> Optional.empty()
		bookTemplate.title == TITLE

	}

	void "parses page that does not have any book sidebar template"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()
		Character character = Mock()

		when:
		BookTemplate bookTemplate = bookTemplatePageProcessor.process(page)

		then:
		1 * bookPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * categoriesBookTemplateEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<CategoryHeader>, BookTemplate> enrichablePair ->
			assert enrichablePair.input == categoryHeaderList
			assert enrichablePair.output != null
		}
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet(character)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_REFERENCE_BOOK, TemplateTitle.SIDEBAR_RPG_BOOK,
				TemplateTitle.SIDEBAR_BIOGRAPHY_BOOK, TemplateTitle.SIDEBAR_AUDIO) >> Optional.empty()
		0 * _
		bookTemplate.title == TITLE
		bookTemplate.page == modelPage
		bookTemplate.characters.contains character
	}

	void "parses page that do have a book sidebar template"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()
		Template.Part templatePart = Mock()
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		Template sidebarBookTemplate = new Template(parts: templatePartList)

		when:
		BookTemplate bookTemplate = bookTemplatePageProcessor.process(page)

		then:
		1 * bookPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * categoriesBookTemplateEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<CategoryHeader>, BookTemplate> enrichablePair ->
			assert enrichablePair.input == categoryHeaderList
			assert enrichablePair.output != null
		}
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_REFERENCE_BOOK, TemplateTitle.SIDEBAR_RPG_BOOK,
				TemplateTitle.SIDEBAR_BIOGRAPHY_BOOK, TemplateTitle.SIDEBAR_AUDIO) >> Optional.of(sidebarBookTemplate)
		1 * bookTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Template, BookTemplate> enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output != null
		}
		0 * _
		bookTemplate.title == TITLE
		bookTemplate.page == modelPage
	}

}
