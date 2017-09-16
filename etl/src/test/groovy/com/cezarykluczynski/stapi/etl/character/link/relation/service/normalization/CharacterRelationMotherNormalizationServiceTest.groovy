package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationMotherNormalizationServiceTest extends Specification {

	private CharacterRelationMotherNormalizationService characterRelationMotherNormalizationService

	void setup() {
		characterRelationMotherNormalizationService = new CharacterRelationMotherNormalizationService(new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationMotherNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName        | relationName
		'biological mother'    | null
		'biological'           | null
		'humanoid'             | null
		'NCC-1701-D'           | null
		'biological, Deceased' | null
		'24th century'         | null
		'deceased'             | null
		'21st century'         | null
		'female'               | null
		'foster'               | CharacterRelationName.FOSTER_MOTHER
		'stepmother'           | CharacterRelationName.STEPMOTHER
		'step-mother'          | CharacterRelationName.STEPMOTHER
		'adoptive'             | CharacterRelationName.ADOPTIVE_MOTHER
		'adopted'              | CharacterRelationName.ADOPTIVE_MOTHER
		'surrogate'            | CharacterRelationName.SURROGATE_MOTHER
	}

}
