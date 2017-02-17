package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractMovieTest

class MovieTemplateToMovieProcessorTest extends AbstractMovieTest {

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
