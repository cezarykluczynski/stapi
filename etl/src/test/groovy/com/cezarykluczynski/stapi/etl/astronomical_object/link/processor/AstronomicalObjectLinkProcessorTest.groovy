package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class AstronomicalObjectLinkProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = 'WIKITEXT'
	private static final String FIRST_LINE = 'Too sort'
	private static final String SECOND_LINE = '{{disambiguation}}'
	private static final String THIRD_LINE = 'First valid line should be over 15 characters.'
	private static final String LOCATION = 'LOCATION'
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final SourcesMediaWikiSource ETL_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private TemplateFinder templateFinderMock

	private ParagraphExtractor paragraphExtractorMock

	private AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessorMock

	private AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessorMock

	private AstronomicalObjectLocationFixedValueProvider astronomicalObjectLocationFixedValueProviderMock

	private AstronomicalObjectLinkProcessor astronomicalObjectLinkProcessor

	private AstronomicalObject astronomicalObjectFromWikitext

	private AstronomicalObject astronomicalObjectFromTemplate

	void setup() {
		pageApiMock = Mock()
		mediaWikiSourceMapperMock = Mock()
		templateFinderMock = Mock()
		paragraphExtractorMock = Mock()
		astronomicalObjectLinkWikitextProcessorMock = Mock()
		astronomicalObjectLinkEnrichingProcessorMock = Mock()
		astronomicalObjectLocationFixedValueProviderMock = Mock()
		astronomicalObjectLinkProcessor = new AstronomicalObjectLinkProcessor(pageApiMock, mediaWikiSourceMapperMock, templateFinderMock,
				paragraphExtractorMock, astronomicalObjectLinkWikitextProcessorMock, astronomicalObjectLinkEnrichingProcessorMock,
				astronomicalObjectLocationFixedValueProviderMock)
		astronomicalObjectFromWikitext = Mock()
		astronomicalObjectFromTemplate = Mock()
	}

	void "does not interact with the remaining dependencies if page is not found"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> null
		0 * _
	}

	void "does not interact with the remaining dependencies if fixed value is found"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		AstronomicalObject location = new AstronomicalObject()

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * astronomicalObjectLocationFixedValueProviderMock.getSearchedValue(astronomicalObject) >> FixedValueHolder.found(location)
		0 * _
		astronomicalObject.location == location
	}

	void "processes wikitext from template"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		AstronomicalObject location = new AstronomicalObject()
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * astronomicalObjectLocationFixedValueProviderMock.getSearchedValue(astronomicalObject) >> FixedValueHolder.notFound()
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.SIDEBAR_ASTRONOMICAL_OBJECT, TemplateTitle.SIDEBAR_PLANET,
				TemplateTitle.SIDEBAR_STAR, TemplateTitle.SIDEBAR_STAR_SYSTEM) >> [template]
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> [location]
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<AstronomicalObject>, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == [location]
				assert enrichablePair.output == astronomicalObject
				astronomicalObject.location = location
		}
		0 * _
		astronomicalObject.location == location
	}

	void "processes wikitext from first valid line"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		AstronomicalObject location = new AstronomicalObject()
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * astronomicalObjectLocationFixedValueProviderMock.getSearchedValue(astronomicalObject) >> FixedValueHolder.notFound()
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.SIDEBAR_ASTRONOMICAL_OBJECT, TemplateTitle.SIDEBAR_PLANET,
				TemplateTitle.SIDEBAR_STAR, TemplateTitle.SIDEBAR_STAR_SYSTEM) >> []
		1 * paragraphExtractorMock.extractLines(WIKITEXT) >> [FIRST_LINE, SECOND_LINE, THIRD_LINE]
		1 * astronomicalObjectLinkWikitextProcessorMock.process(THIRD_LINE) >> [location]
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<AstronomicalObject>, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == [location]
				assert enrichablePair.output == astronomicalObject
				astronomicalObject.location = location
		}
		0 * _
		astronomicalObject.location == location
	}

	void "processes wikitext from bginfo template"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		AstronomicalObject location = new AstronomicalObject()
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template bgInfoTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(key: '1', value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * astronomicalObjectLocationFixedValueProviderMock.getSearchedValue(astronomicalObject) >> FixedValueHolder.notFound()
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.SIDEBAR_ASTRONOMICAL_OBJECT, TemplateTitle.SIDEBAR_PLANET,
				TemplateTitle.SIDEBAR_STAR, TemplateTitle.SIDEBAR_STAR_SYSTEM) >> []
		1 * paragraphExtractorMock.extractLines(WIKITEXT) >> [FIRST_LINE, SECOND_LINE]
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.BGINFO) >> [bgInfoTemplate]
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> [location]
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<AstronomicalObject>, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == [location]
				assert enrichablePair.output == astronomicalObject
				astronomicalObject.location = location
		}
		0 * _
		astronomicalObject.location == location
	}

	void "when everything fails, location stays null"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * astronomicalObjectLocationFixedValueProviderMock.getSearchedValue(astronomicalObject) >> FixedValueHolder.notFound()
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.SIDEBAR_ASTRONOMICAL_OBJECT, TemplateTitle.SIDEBAR_PLANET,
				TemplateTitle.SIDEBAR_STAR, TemplateTitle.SIDEBAR_STAR_SYSTEM) >> []
		1 * paragraphExtractorMock.extractLines(WIKITEXT) >> [FIRST_LINE, SECOND_LINE]
		1 * templateFinderMock.findTemplates(sourcesPage, TemplateTitle.BGINFO) >> []
		0 * _
		astronomicalObject.location == null
	}

}
