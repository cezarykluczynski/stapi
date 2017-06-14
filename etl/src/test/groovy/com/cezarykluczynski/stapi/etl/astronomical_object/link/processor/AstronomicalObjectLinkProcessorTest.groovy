package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
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
	private static final String LOCATION = 'LOCATION'
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final SourcesMediaWikiSource ETL_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private TemplateFinder templateFinderMock

	private ParagraphExtractor paragraphExtractorMock

	private AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessorMock

	private AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessorMock

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
		astronomicalObjectLinkProcessor = new AstronomicalObjectLinkProcessor(pageApiMock, mediaWikiSourceMapperMock, templateFinderMock,
				paragraphExtractorMock, astronomicalObjectLinkWikitextProcessorMock, astronomicalObjectLinkEnrichingProcessorMock)
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

	void "processes wikitext from first paragraph and from planet template, but does not interact with enricher, when both objects were null"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		1 * astronomicalObjectLinkWikitextProcessorMock.process(WIKITEXT) >> null
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> null
		0 * _
	}

	void "passes wikitext processing result to enricher"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		1 * astronomicalObjectLinkWikitextProcessorMock.process(WIKITEXT) >> astronomicalObjectFromWikitext
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> null
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<AstronomicalObject, AstronomicalObject> enrichablePair ->
			assert enrichablePair.input == astronomicalObjectFromWikitext
			assert enrichablePair.output == astronomicalObject
		}
		0 * _
	}

	void "passes template processing result to enricher"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		1 * astronomicalObjectLinkWikitextProcessorMock.process(WIKITEXT) >> null
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> astronomicalObjectFromTemplate
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<AstronomicalObject, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == astronomicalObjectFromTemplate
				assert enrichablePair.output == astronomicalObject
		}
		0 * _
	}

	void "passes result to enricher when wikitext processing result and template processing result are the same"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		1 * astronomicalObjectLinkWikitextProcessorMock.process(WIKITEXT) >> astronomicalObjectFromTemplate
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> astronomicalObjectFromTemplate
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<AstronomicalObject, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == astronomicalObjectFromTemplate
				assert enrichablePair.output == astronomicalObject
		}
		0 * _
	}

	void "passes template processing result to enricher, when template processing result and wikitext processing result differs"() {
		given:
		ModelPage page = new ModelPage(title: TITLE, mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE)
		AstronomicalObject astronomicalObject = new AstronomicalObject(page: page)
		SourcesPage sourcesPage = new SourcesPage(wikitext: WIKITEXT)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: AstronomicalObjectLinkProcessor.LOCATION, value: LOCATION)
		))

		when:
		astronomicalObjectLinkProcessor.process(astronomicalObject)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> ETL_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, ETL_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		1 * astronomicalObjectLinkWikitextProcessorMock.process(WIKITEXT) >> astronomicalObjectFromWikitext
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectLinkWikitextProcessorMock.process(LOCATION) >> astronomicalObjectFromTemplate
		1 * astronomicalObjectLinkEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<AstronomicalObject, AstronomicalObject> enrichablePair ->
				assert enrichablePair.input == astronomicalObjectFromTemplate
				assert enrichablePair.output == astronomicalObject
		}
		0 * _
	}

}
