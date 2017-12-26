package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.comic_strip.creation.service.ComicStripCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.comics.creation.service.ComicsPageFilter
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.processor.character.WikitextSectionsCharactersProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class ComicsTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_COMICS = 'TITLE (comic)'
	private static final String TITLE_FOTONOVEL = 'TITLE (fotonovel)'
	private static final String TITLE_OMNIBUS = 'TITLE (omnibus)'

	private ComicsPageFilter comicsPageFilterMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringServiceMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private ComicsTemplateCompositeEnrichingProcessor comicsTemplateCompositeEnrichingProcessorMock

	private ComicsTemplatePartsEnrichingProcessor comicsTemplatePartsEnrichingProcessorMock

	private WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessorMock

	private PageCacheStorage pageCacheStorageMock

	private ComicsTemplatePageProcessor comicsTemplatePageProcessor

	void setup() {
		comicsPageFilterMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		comicStripCandidatePageGatheringServiceMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		comicsTemplateCompositeEnrichingProcessorMock = Mock()
		comicsTemplatePartsEnrichingProcessorMock = Mock()
		wikitextSectionsCharactersProcessorMock = Mock()
		pageCacheStorageMock = Mock()
		comicsTemplatePageProcessor = new ComicsTemplatePageProcessor(comicsPageFilterMock, categoryTitlesExtractingProcessorMock,
				comicStripCandidatePageGatheringServiceMock, pageBindingServiceMock, templateFinderMock,
				comicsTemplateCompositeEnrichingProcessorMock, comicsTemplatePartsEnrichingProcessorMock, wikitextSectionsCharactersProcessorMock,
				pageCacheStorageMock)
	}

	void "returns null when ComicsPageFilter returns true"() {
		given:
		Page page = Mock()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		comicsTemplate == null
	}

	void "sets photonovel flag when photonovels category is found"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PHOTONOVELS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.photonovel
	}

	void "sets photonovel flag when photonovels collection category is found"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PHOTONOVELS_COLLECTIONS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.photonovel
	}

	void "sets adaptation flag when adaptation category is found"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.COMIC_ADAPTATIONS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.adaptation
	}

	void "clears title when it contains '(comic)'"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_COMICS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PHOTONOVELS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
	}

	void "clears title when it contains '(fotonovel)'"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_FOTONOVEL,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PHOTONOVELS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >>
				Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
	}

	void "clears title when it contains '(omnibus)'"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_OMNIBUS,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PHOTONOVELS)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >>
				Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
	}

	@SuppressWarnings('BracesForMethod')
	void """returns null when sidebar comic strip template is found, and adds page to ComicStripCandidatePageGatheringService,
			and to PageCacheStorage"""() {
		given:
		Page page = new Page(title: TITLE)
		Template comisStripTemplate = Mock()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.of(comisStripTemplate)
		1 * comicStripCandidatePageGatheringServiceMock.addCandidate(page)
		1 * pageCacheStorageMock.put(page)
		0 * _
		comicsTemplate == null
	}

	void "parses page that does not have sidebar comics template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()
		Character character = Mock()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >>
				Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet(character)
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
		comicsTemplate.characters.contains character
	}

	void "parses page with sidebar comics template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()
		Template.Part templatePart = Mock()
		Template template = new Template(parts: Lists.newArrayList(templatePart))

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * comicsPageFilterMock.shouldBeFilteredOut(page) >> false
		2 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * wikitextSectionsCharactersProcessorMock.process(page) >> Sets.newHashSet()
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_COMIC, TemplateTitle.SIDEBAR_NOVEL, TemplateTitle.SIDEBAR_AUDIO) >>
				Optional.of(template)
		1 * comicsTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, ComicsTemplate> enrichablePair ->
			assert enrichablePair.input[0] == templatePart
			assert enrichablePair.output != null
		}
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
	}

}
