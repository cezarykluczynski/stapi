package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationSiblingNormalizationServiceTest extends Specification {

	private CharacterRelationSiblingNormalizationService characterRelationSiblingNormalizationService

	void setup() {
		characterRelationSiblingNormalizationService = new CharacterRelationSiblingNormalizationService(new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationSiblingNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName           | relationName
		'by Lwaxana'              | null
		'biological'              | null
		'24th century'            | null
		'deceased'                | null
		'Augment'                 | null
		'half-brother'            | CharacterRelationName.HALF_BROTHER
		'brother'                 | CharacterRelationName.BROTHER
		'Brother'                 | CharacterRelationName.BROTHER
		'Older "Brother"'         | CharacterRelationName.BROTHER
		'older brother'           | CharacterRelationName.BROTHER
		'Younger "Brother"'       | CharacterRelationName.BROTHER
		'\'\'older "Brother"\'\'' | CharacterRelationName.BROTHER
		'Brother, Deceased'       | CharacterRelationName.BROTHER
		'brother; deceased'       | CharacterRelationName.BROTHER
		'brother, adopted'        | CharacterRelationName.ADOPTIVE_BROTHER
		'transporter duplicate'   | CharacterRelationName.TRANSPORTER_DUPLICATE
		'clone'                   | CharacterRelationName.CLONE
		'sister'                  | CharacterRelationName.SISTER
		'sister, adopted'         | CharacterRelationName.ADOPTIVE_SISTER
		'half-sister'             | CharacterRelationName.HALF_SISTER
		'adopted'                 | CharacterRelationName.ADOPTIVE_SIBLING
	}

}
