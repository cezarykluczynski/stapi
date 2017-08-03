package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.template.common.factory.GenreFactory
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import spock.lang.Specification

class VideoGameTemplateGenresProcessorTest extends Specification {

	private GenreFactory genreFactoryMock

	private VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessor

	void setup() {
		genreFactoryMock = Mock()
		videoGameTemplateGenresProcessor = new VideoGameTemplateGenresProcessor(genreFactoryMock)
	}

	void "splits string by commas, then passes every part to GenreFactory, then returns set of non-null genres"() {
		given:
		String genreName1 = 'Action'
		String genreName2 = 'Strategy'
		String genreName3 = 'Puzzle'
		String combinedGenres = "${genreName1}, ${genreName2}, ${genreName3}"
		Genre genre1 = Mock()
		Genre genre3 = Mock()

		when:
		Set<Genre> genreSet = videoGameTemplateGenresProcessor.process(combinedGenres)

		then:
		1 * genreFactoryMock.createForName(genreName1) >> genre1
		1 * genreFactoryMock.createForName(genreName2) >> null
		1 * genreFactoryMock.createForName(genreName3) >> genre3
		0 * _
		genreSet.size() == 2
		genreSet.contains genre1
		genreSet.contains genre3
	}

}
