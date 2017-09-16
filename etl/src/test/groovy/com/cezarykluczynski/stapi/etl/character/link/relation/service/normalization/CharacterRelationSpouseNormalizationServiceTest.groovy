package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationSpouseNormalizationServiceTest extends Specification {

	private CharacterRelationSpouseNormalizationService characterRelationSpouseNormalizationService

	void setup() {
		characterRelationSpouseNormalizationService = new CharacterRelationSpouseNormalizationService(new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationSpouseNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName              | relationName
		'24th century'               | null
		'widowed'                    | null
		'2154-2154'                  | null
		'20th century'               | null
		'deceased 2368'              | null
		'annulled'                   | null
		'later annulled'             | null
		'deceased 2267'              | null
		'deceased'                   | null
		'separated'                  | null
		'née Conway'                 | null
		'neé Gessard'                | null
		'Deceased'                   | null
		'21st century'               | null
		'holographic'                | null
		'Human - alternate timeline' | null
		'divorced'                   | CharacterRelationName.EX_SPOUSE
		'former'                     | CharacterRelationName.EX_SPOUSE
		'partner'                    | CharacterRelationName.PARTNER
		'first wife - died'          | CharacterRelationName.WIFE
		'wife'                       | CharacterRelationName.WIFE
		'husband'                    | CharacterRelationName.HUSBAND
		'Husband'                    | CharacterRelationName.HUSBAND
		'consort'                    | CharacterRelationName.CONSORT
		'ex'                         | CharacterRelationName.EX_SPOUSE
	}

}
