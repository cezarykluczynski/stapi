package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationFatherNormalizationServiceTest extends Specification {

	private CharacterRelationFatherNormalizationService characterRelationFatherNormalizationService

	void setup() {
		characterRelationFatherNormalizationService = new CharacterRelationFatherNormalizationService(new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationFatherNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName         | relationName
		'biological'            | null
		'24th century'          | null
		'biological, Deceased'  | null
		'father of Kelemane'    | null
		'NCC-1701-D'            | null
		'deceased'              | null
		'biological; deceased'  | null
		'Winona Kirk\'s father' | null
		'Professor'             | null
		'Male Admiral'          | null
		'creator'               | CharacterRelationName.CREATOR
		'foster'                | CharacterRelationName.FOSTER_FATHER
		'adoptive'              | CharacterRelationName.ADOPTIVE_FATHER
		'adopted'               | CharacterRelationName.ADOPTIVE_FATHER
		'adoptive father'       | CharacterRelationName.ADOPTIVE_FATHER
	}

}
