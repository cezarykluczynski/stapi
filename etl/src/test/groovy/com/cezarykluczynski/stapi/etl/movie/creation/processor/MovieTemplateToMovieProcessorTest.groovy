package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.page.entity.Page
import spock.lang.Specification

import java.time.LocalDate

class MovieTemplateToMovieProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_BULGARIAN = 'TITLE_BULGARIAN'
	private static final String TITLE_CATALAN = 'TITLE_CATALAN'
	private static final String TITLE_CHINESE_TRADITIONAL = 'TITLE_CHINESE_TRADITIONAL'
	private static final String TITLE_GERMAN = 'TITLE_GERMAN'
	private static final String TITLE_ITALIAN = 'TITLE_ITALIAN'
	private static final String TITLE_JAPANESE = 'TITLE_JAPANESE'
	private static final String TITLE_POLISH = 'TITLE_POLISH'
	private static final String TITLE_RUSSIAN = 'TITLE_RUSSIAN'
	private static final String TITLE_SERBIAN = 'TITLE_SERBIAN'
	private static final String TITLE_SPANISH = 'TITLE_SPANISH'
	private static final String GUID = 'GUID'
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 234.5F
	private static final Integer YEAR_FROM = 2368
	private static final Integer YEAR_TO = 2369
	private static final LocalDate US_RELEASE_DATE = LocalDate.of(1995, 4, 8)

	private final Page page = Mock(Page)

	private GuidGenerator guidGeneratorMock

	private MovieTemplateToMovieProcessor movieTemplateToMovieProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		movieTemplateToMovieProcessor = new MovieTemplateToMovieProcessor(guidGeneratorMock)
	}

	void "converts EpisodeTemplate to Episode"() {
		given:
		Movie movie = new Movie()
		MovieTemplate movieTemplate = new MovieTemplate(
				page: page,
				movieStub: movie,
				title: TITLE,
				titleBulgarian: TITLE_BULGARIAN,
				titleCatalan: TITLE_CATALAN,
				titleChineseTraditional: TITLE_CHINESE_TRADITIONAL,
				titleGerman: TITLE_GERMAN,
				titleItalian: TITLE_ITALIAN,
				titleJapanese: TITLE_JAPANESE,
				titlePolish: TITLE_POLISH,
				titleRussian: TITLE_RUSSIAN,
				titleSerbian: TITLE_SERBIAN,
				titleSpanish: TITLE_SPANISH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usReleaseDate: US_RELEASE_DATE)

		when:
		Movie movieOutput = movieTemplateToMovieProcessor.process(movieTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, Movie) >> GUID
		movieOutput == movie
		movieOutput.guid == GUID
		movieOutput.page == page
		movieOutput.title == TITLE
		movieOutput.titleBulgarian == TITLE_BULGARIAN
		movieOutput.titleCatalan == TITLE_CATALAN
		movieOutput.titleChineseTraditional == TITLE_CHINESE_TRADITIONAL
		movieOutput.titleGerman == TITLE_GERMAN
		movieOutput.titleItalian == TITLE_ITALIAN
		movieOutput.titleJapanese == TITLE_JAPANESE
		movieOutput.titlePolish == TITLE_POLISH
		movieOutput.titleRussian == TITLE_RUSSIAN
		movieOutput.titleSerbian == TITLE_SERBIAN
		movieOutput.titleSpanish == TITLE_SPANISH
		movieOutput.stardateFrom == STARDATE_FROM
		movieOutput.stardateTo == STARDATE_TO
		movieOutput.yearFrom == YEAR_FROM
		movieOutput.yearTo == YEAR_TO
		movieOutput.usReleaseDate == US_RELEASE_DATE
	}

}
