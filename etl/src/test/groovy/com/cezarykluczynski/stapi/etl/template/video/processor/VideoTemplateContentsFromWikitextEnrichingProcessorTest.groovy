package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.MovieTitle
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class VideoTemplateContentsFromWikitextEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private MovieRepository movieRepositoryMock

	private SeasonRepository seasonRepositoryMock

	private TemplateFinder templateFinderMock

	private WikitextApi wikitextApiMock

	VideoTemplateContentsFromWikitextEnrichingProcessor videoTemplateContentsFromWikitextEnrichingProcessor

	void setup() {
		movieRepositoryMock = Mock()
		seasonRepositoryMock = Mock()
		templateFinderMock = Mock()
		wikitextApiMock = Mock()
		videoTemplateContentsFromWikitextEnrichingProcessor = new VideoTemplateContentsFromWikitextEnrichingProcessor(movieRepositoryMock,
				seasonRepositoryMock, templateFinderMock, wikitextApiMock)
	}

	void "adds movie when page title equals before brackets"() {
		given:
		Movie theWrathOfKhan = new Movie(id: 2L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(2L))
		Movie theSearchForSpock = new Movie(id: 3L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(3L))
		Page page = new Page(title: 'Star Trek III: The Search for Spock (Betamax)')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> [theWrathOfKhan, theSearchForSpock]
		1 * seasonRepositoryMock.findAll() >> []

		then:
		1 * movieRepositoryMock.findById(3L) >> Optional.of(theSearchForSpock)
		0 * _
		videoTemplate.movies == Set.of(theSearchForSpock)
	}

	void "adds movie when page title starts with known movie title"() {
		given:
		Movie insurrection = new Movie(id: 9L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(9L))
		Movie nemesis = new Movie(id: 10L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(10L))
		Page page = new Page(title: 'Star Trek Nemesis & Trekkies (DVD)')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> [insurrection, nemesis]
		1 * seasonRepositoryMock.findAll() >> []

		then:
		1 * movieRepositoryMock.findById(10L) >> Optional.of(nemesis)
		0 * _
		videoTemplate.movies == Set.of(nemesis)
	}

	void "adds movies from wikitext page links"() {
		given:
		Movie theMotionPicture = new Movie(id: 1L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(1L))
		Movie theWrathOfKhan = new Movie(id: 2L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(2L))
		Movie theSearchForSpock = new Movie(id: 3L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(3L))
		Page page = new Page(title: 'Star Trek - The Original Crew Movie Collection (2001)', sections: [
				new PageSection(anchor: RandomUtil.randomItem(VideoTemplateContentsFromWikitextEnrichingProcessor.MOVIE_CONTENTS_SECTIONS),
						wikitext: "${WIKITEXT}{{Video nav...")
		])
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> [theMotionPicture, theWrathOfKhan, theSearchForSpock]
		1 * seasonRepositoryMock.findAll() >> []

		then:
		2 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> [new PageLink(title: theMotionPicture.title),
				new PageLink(title: theWrathOfKhan.title), new PageLink(title: theSearchForSpock.title)]
		1 * movieRepositoryMock.findById(1L) >> Optional.of(theMotionPicture)
		1 * movieRepositoryMock.findById(2L) >> Optional.of(theWrathOfKhan)
		1 * movieRepositoryMock.findById(3L) >> Optional.of(theSearchForSpock)
		1 * templateFinderMock.findTemplates(page, TemplateTitle.FILM) >> []
		0 * _
		videoTemplate.movies == Set.of(theMotionPicture, theWrathOfKhan, theSearchForSpock)
	}

	void "adds movies from full page wikitext"() {
		given:
		Movie theMotionPicture = new Movie(id: 1L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(1L))
		Movie theWrathOfKhan = new Movie(id: 2L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(2L))
		Movie theSearchForSpock = new Movie(id: 3L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(3L))
		Page page = new Page(title: 'Star Trek - The Motion Pictures DVD Collection (2001)', wikitext: "${WIKITEXT}{{Video nav...")
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> [theMotionPicture, theWrathOfKhan, theSearchForSpock]
		1 * seasonRepositoryMock.findAll() >> []

		then:
		2 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> [new PageLink(title: theMotionPicture.title),
				new PageLink(title: theWrathOfKhan.title), new PageLink(title: theSearchForSpock.title)]
		1 * movieRepositoryMock.findById(1L) >> Optional.of(theMotionPicture)
		1 * movieRepositoryMock.findById(2L) >> Optional.of(theWrathOfKhan)
		1 * movieRepositoryMock.findById(3L) >> Optional.of(theSearchForSpock)
		1 * templateFinderMock.findTemplates(page, TemplateTitle.FILM) >> []
		0 * _
		videoTemplate.movies == Set.of(theMotionPicture, theWrathOfKhan, theSearchForSpock)
	}

	void "adds seasons from wikitext page links"() {
		given:
		Season tosSeason1 = new Season(id: 1L, title: 'TOS Season 1')
		Season tosSeason2 = new Season(id: 2L, title: 'TOS Season 2')
		Season tosSeason3 = new Season(id: 3L, title: 'TOS Season 3')
		Page page = new Page(title: 'Star Trek: The Original Series (VHS)', sections: [
				new PageSection(anchor: RandomUtil.randomItem(VideoTemplateContentsFromWikitextEnrichingProcessor.MOVIE_CONTENTS_SECTIONS),
						wikitext: "${WIKITEXT}{{Video nav...")
		])
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> []
		1 * seasonRepositoryMock.findAll() >> [tosSeason1, tosSeason2, tosSeason3]

		then:
		2 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> [new PageLink(title: tosSeason1.title),
				 new PageLink(title: tosSeason2.title), new PageLink(title: tosSeason3.title)]
		1 * seasonRepositoryMock.findById(1L) >> Optional.of(tosSeason1)
		1 * seasonRepositoryMock.findById(2L) >> Optional.of(tosSeason2)
		1 * seasonRepositoryMock.findById(3L) >> Optional.of(tosSeason3)
		1 * templateFinderMock.findTemplates(page, TemplateTitle.FILM) >> []
		0 * _
		videoTemplate.seasons == Set.of(tosSeason1, tosSeason2, tosSeason3)
	}

	void "adds movies from film templates"() {
		given:
		Template filmTemplate1 = new Template(parts: [new Template.Part(value: '1')])
		Template filmTemplate2 = new Template(parts: [new Template.Part(value: '2')])
		Template filmTemplate3 = new Template(parts: [new Template.Part(value: 'NOPE')])
		Template filmTemplate4 = new Template(parts: [new Template.Part(value: '3')])
		Movie theMotionPicture = new Movie(id: 1L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(1L))
		Movie theWrathOfKhan = new Movie(id: 2L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(2L))
		Movie theSearchForSpock = new Movie(id: 3L, title: MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(3L))
		Page page = new Page(title: 'TOS First Three Movies', sections: [
				new PageSection(anchor: RandomUtil.randomItem(VideoTemplateContentsFromWikitextEnrichingProcessor.MOVIE_CONTENTS_SECTIONS),
						wikitext: "${WIKITEXT}{{Video nav...")
		])
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findAll() >> [theMotionPicture, theWrathOfKhan, theSearchForSpock]
		1 * seasonRepositoryMock.findAll() >> []

		then:
		2 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> []
		1 * templateFinderMock.findTemplates(page, TemplateTitle.FILM) >> [filmTemplate1, filmTemplate2, filmTemplate3, filmTemplate4]

		1 * movieRepositoryMock.findById(1L) >> Optional.of(theMotionPicture)
		1 * movieRepositoryMock.findById(2L) >> Optional.of(theWrathOfKhan)
		1 * movieRepositoryMock.findById(3L) >> Optional.of(theSearchForSpock)
		0 * _
		videoTemplate.movies == Set.of(theMotionPicture, theWrathOfKhan, theSearchForSpock)
	}

}
