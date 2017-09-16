package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationChildrenNormalizationServiceTest extends Specification {

	private CharacterRelationChildrenNormalizationService characterRelationChildrenNormalizationService

	void setup() {
		characterRelationChildrenNormalizationService = new CharacterRelationChildrenNormalizationService(
				new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationChildrenNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName                        | relationName
		'holographic'                          | null
		'24th century'                         | null
		'\'\'" Children "\'\''                 | null
		'Junior'                               | null
		'deceased'                             | null
		'Deceased'                             | null
		'alien being, also deceased'           | null
		'by his Cardassian wife'               | null
		'\'\'with Amanda Grayson\'\''          | null
		'first marriage'                       | null
		'maternal surrogate to Keiko O\'Brien' | null
		'alternate'                            | null
		'son'                                  | CharacterRelationName.SON
		'sons'                                 | CharacterRelationName.SON
		'Son'                                  | CharacterRelationName.SON
		'\'\'" Son "\'\''                      | CharacterRelationName.SON
		'stepson'                              | CharacterRelationName.STEPSON
		'Stepson'                              | CharacterRelationName.STEPSON
		'step-son'                             | CharacterRelationName.STEPSON
		'Daughter'                             | CharacterRelationName.DAUGHTER
		'daughter'                             | CharacterRelationName.DAUGHTER
		'\'\'" Daughter "\'\''                 | CharacterRelationName.DAUGHTER
		'daughter, deceased'                   | CharacterRelationName.DAUGHTER
		'" Granddaughter "'                    | CharacterRelationName.GRANDDAUGHTER
		'Adoptive'                             | CharacterRelationName.ADOPTIVE_CHILD
		'adopted'                              | CharacterRelationName.ADOPTIVE_CHILD
		'transporter duplicate'                | CharacterRelationName.TRANSPORTER_DUPLICATE
	}

}
