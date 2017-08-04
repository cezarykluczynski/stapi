package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.etl.genre.service.GenreNameNormalizationService
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import com.cezarykluczynski.stapi.model.genre.repository.GenreRepository
import spock.lang.Specification

class GenreFactoryTest extends Specification {

	private static final RAW_NAME = 'RAW_NAME'
	private static final NORMALIZED_NAME = 'NORMALIZED_NAME'
	private static final UID = 'UID'

	private GenreNameNormalizationService genreNameNormalizationServiceMock

	private GenreRepository genreRepositoryMock

	private UidGenerator uidGeneratorMock

	private GenreFactory genreFactory

	void setup() {
		genreNameNormalizationServiceMock = Mock()
		genreRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		genreFactory = new GenreFactory(genreNameNormalizationServiceMock, genreRepositoryMock, uidGeneratorMock)
	}

	void "returns existing Genre when it can be found"() {
		given:
		Genre genre = Mock()

		when:
		Genre genreOutput = genreFactory.createForName(RAW_NAME)

		then:
		1 * genreNameNormalizationServiceMock.normalize(RAW_NAME) >> NORMALIZED_NAME
		1 * genreRepositoryMock.findByName(NORMALIZED_NAME) >> Optional.of(genre)
		0 * _
		genreOutput == genre
	}

	void "creates Genre when none can be found"() {
		when:
		Genre genre = genreFactory.createForName(RAW_NAME)

		then:
		1 * genreNameNormalizationServiceMock.normalize(RAW_NAME) >> NORMALIZED_NAME
		1 * genreRepositoryMock.findByName(NORMALIZED_NAME) >> Optional.empty()
		1 * genreRepositoryMock.save(_ as Genre) >> { Genre genreInput ->
			assert genreInput.name == NORMALIZED_NAME
			assert genreInput.uid == UID
		}
		1 * uidGeneratorMock.generateForGenre(NORMALIZED_NAME) >> UID
		0 * _
		genre.name == NORMALIZED_NAME
		genre.uid == UID
	}

}
