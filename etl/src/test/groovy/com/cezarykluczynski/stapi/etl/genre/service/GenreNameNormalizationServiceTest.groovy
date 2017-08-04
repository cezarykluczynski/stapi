package com.cezarykluczynski.stapi.etl.genre.service

import spock.lang.Specification

class GenreNameNormalizationServiceTest extends Specification {

	private GenreNameNormalizationService genreNameNormalizationService

	void setup() {
		genreNameNormalizationService = new GenreNameNormalizationService()
	}

	void "normalizes genre name"() {
		expect:
		genreNameNormalizationService.normalize(genreName) == normalizedGenreName

		where:
		genreName | normalizedGenreName
		'Interactive movie' | 'Interactive movie'
		'Action' | 'Action'
		'real-time strategy' | 'Real-time strategy'
		'Real-time strategy' | 'Real-time strategy'
		'Real-time Strategy' | 'Real-time strategy'
		'Real-Time Strategy' | 'Real-time strategy'
		'Strategy RPG' | 'Strategy RPG'
		'Action/Adventure' | 'Action adventure'
		'Shoot \'em up' | 'Shoot \'em up'
		'action adventure' | 'Action adventure'
	}

}
