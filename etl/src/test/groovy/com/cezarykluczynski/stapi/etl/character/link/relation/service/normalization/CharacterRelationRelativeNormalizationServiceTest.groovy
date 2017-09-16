package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationRelativeNormalizationServiceTest extends Specification {

	private CharacterRelationRelativeNormalizationService characterRelationRelativeNormalizationService

	void setup() {
		characterRelationRelativeNormalizationService = new CharacterRelationRelativeNormalizationService(
				new PunctuationIgnoringWeightedLevenshtein())
	}

	@Unroll('when #rawRelationName is passed, #relationName is returned')
	"when raw relation name is passed, normalized relation name is returned"() {
		expect:
		characterRelationRelativeNormalizationService.normalize(rawRelationName) == relationName

		where:
		rawRelationName                  | relationName
		'maternal'                       | null
		'deceased'                       | null
		'22nd century'                   | null
		'"Male Admiral'                  | null
		'names unknown'                  | null
		'daughter of Sek'                | null
		'member of respective house'     | null
		'24th century'                   | null
		'Winona Kirk\'s father'          | null
		'paternal great-grandfather'     | CharacterRelationName.PATERNAL_GREAT_GRANDFATHER
		'"Grandfather"'                  | CharacterRelationName.GRANDFATHER
		'Great-Grandson'                 | CharacterRelationName.GREAT_GRANDSON
		'former partner'                 | CharacterRelationName.FORMER_PARTNER
		'brother-in-law, deceased'       | CharacterRelationName.BROTHER_IN_LAW
		'cousins'                        | CharacterRelationName.COUSIN
		'Cousin'                         | CharacterRelationName.COUSIN
		'maternal great-grandmother'     | CharacterRelationName.MATERNAL_GREAT_GRANDMOTHER
		'Paternal grandfather'           | CharacterRelationName.PATERNAL_GRANDFATHER
		'paternal grandfather'           | CharacterRelationName.PATERNAL_GRANDFATHER
		'ancestor'                       | CharacterRelationName.ANCESTOR
		'descendant'                     | CharacterRelationName.DESCENDANT
		'nephew, deceased'               | CharacterRelationName.NEPHEW
		'aunt'                           | CharacterRelationName.AUNT
		'aunts'                          | CharacterRelationName.AUNT
		'Father-in-law'                  | CharacterRelationName.FATHER_IN_LAW
		'niece'                          | CharacterRelationName.NIECE
		'great-grandfather'              | CharacterRelationName.GREAT_GRANDFATHER
		'great-grandmother'              | CharacterRelationName.GREAT_GRANDMOTHER
		'mother-in-law'                  | CharacterRelationName.MOTHER_IN_LAW
		'great-grandfather'              | CharacterRelationName.GREAT_GRANDFATHER
		'Maternal grandmother'           | CharacterRelationName.MATERNAL_GRANDMOTHER
		'daughter-in-law'                | CharacterRelationName.DAUGHTER_IN_LAW
		'ex-stepfather'                  | CharacterRelationName.EX_STEPFATHER
		'sister-in-law'                  | CharacterRelationName.SISTER_IN_LAW
		'Daughter in-law'                | CharacterRelationName.DAUGHTER_IN_LAW
		'great-great-granddaughter'      | CharacterRelationName.GREAT_GREAT_GRANDDAUGHTER
		'mother'                         | CharacterRelationName.MOTHER
		'brother, deceased'              | CharacterRelationName.BROTHER
		'\'\'"Grandfather"\'\''          | CharacterRelationName.GRANDFATHER
		'son-in-law'                     | CharacterRelationName.SON_IN_LAW
		'uncle'                          | CharacterRelationName.UNCLE
		'Grandson-in-law'                | CharacterRelationName.GRANDSON_IN_LAW
		'grandmother'                    | CharacterRelationName.GRANDMOTHER
		'paternal grandmother'           | CharacterRelationName.PATERNAL_GRANDMOTHER
		'adoptive grandparents'          | CharacterRelationName.ADOPTIVE_GRANDPARENT
		'maternal grandfather'           | CharacterRelationName.MATERNAL_GRANDFATHER
		'\'\'Ancestor\'\''               | CharacterRelationName.ANCESTOR
		'father in-law'                  | CharacterRelationName.FATHER_IN_LAW
		'godson'                         | CharacterRelationName.GODSON
		'foremother'                     | CharacterRelationName.FOREMOTHER
		'grandson'                       | CharacterRelationName.GRANDSON
		'Niece, Deceased'                | CharacterRelationName.NIECE
		'"Niece"'                        | CharacterRelationName.NIECE
		'DNA donor'                      | CharacterRelationName.DNA_DONOR
		'great-granddaughter'            | CharacterRelationName.GREAT_GRANDDAUGHTER
		'granddaughter'                  | CharacterRelationName.GRANDDAUGHTER
		'brother-in-law'                 | CharacterRelationName.BROTHER_IN_LAW
		'daughter-in-law, deceased'      | CharacterRelationName.DAUGHTER_IN_LAW
		'"Uncle"'                        | CharacterRelationName.UNCLE
		'Great-Grandfather'              | CharacterRelationName.GREAT_GRANDFATHER
		'Mother-in-law'                  | CharacterRelationName.MOTHER_IN_LAW
		'Great-grandmother'              | CharacterRelationName.GREAT_GRANDMOTHER
		'\'\'"Ancestor"\'\''             | CharacterRelationName.ANCESTOR
		'adoptive uncle'                 | CharacterRelationName.ADOPTIVE_UNCLE
		'Daughter-in-law'                | CharacterRelationName.DAUGHTER_IN_LAW
		'sister-in-law, deceased'        | CharacterRelationName.SISTER_IN_LAW
		'\'\'Descendant\'\''             | CharacterRelationName.DESCENDANT
		'maternal great-grandfather'     | CharacterRelationName.MATERNAL_GREAT_GRANDFATHER
		'daughter-in-law, deceased 2357' | CharacterRelationName.DAUGHTER_IN_LAW
		'great-grandson'                 | CharacterRelationName.GREAT_GRANDSON
		'paternal ancestor'              | CharacterRelationName.PATERNAL_ANCESTOR
		'great-great-grandfather'        | CharacterRelationName.GREAT_GREAT_GRANDFATHER
		'DNA template'                   | CharacterRelationName.DNA_TEMPLATE
		'grandfather'                    | CharacterRelationName.GRANDFATHER
		'maternal grandmother'           | CharacterRelationName.MATERNAL_GRANDMOTHER
		'"Grandmother"'                  | CharacterRelationName.GRANDMOTHER
		'third foremother'               | CharacterRelationName.THIRD_FOREMOTHER
		'godparents'                     | CharacterRelationName.GODPARENT
		'\'\'"Granddaughter"\'\''        | CharacterRelationName.GRANDDAUGHTER
		'"grandson"'                     | CharacterRelationName.GRANDSON
		'paternal uncle'                 | CharacterRelationName.PATERNAL_UNCLE
		'\'\'"Descendant"\'\''           | CharacterRelationName.DESCENDANT
		'Grandson'                       | CharacterRelationName.GRANDSON
		'cousin'                         | CharacterRelationName.COUSIN
		'paternal aunt'                  | CharacterRelationName.PATERNAL_AUNT
		'"Ancestor"'                     | CharacterRelationName.ANCESTOR
		'Maternal grandfather'           | CharacterRelationName.MATERNAL_GRANDFATHER
		'Grandfather'                    | CharacterRelationName.GRANDFATHER
		'nephew'                         | CharacterRelationName.NEPHEW
		'Great-grandfather'              | CharacterRelationName.GREAT_GRANDFATHER
		'maternal ancestor'              | CharacterRelationName.MATERNAL_ANCESTOR
		'stepsons'                       | CharacterRelationName.STEPSON
		'grandchild'                     | CharacterRelationName.GRANDCHILD
		'Paternal grandmother'           | CharacterRelationName.PATERNAL_GRANDMOTHER
		'"Great Grandfather"'            | CharacterRelationName.GREAT_GRANDFATHER
		'father-in-law'                  | CharacterRelationName.FATHER_IN_LAW
		'sister'                         | CharacterRelationName.SISTER
		'Father'                         | CharacterRelationName.FATHER
		'daughter'                       | CharacterRelationName.DAUGHTER
		'Mother'                         | CharacterRelationName.MOTHER
		'Sister'                         | CharacterRelationName.SISTER
		'father'                         | CharacterRelationName.FATHER
		'brother'                        | CharacterRelationName.BROTHER
		'Brother'                        | CharacterRelationName.BROTHER
	}

}
