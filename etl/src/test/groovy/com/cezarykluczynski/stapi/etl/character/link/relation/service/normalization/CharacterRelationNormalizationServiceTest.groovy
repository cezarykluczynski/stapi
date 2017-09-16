package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.template.character.dto.CommonCharacterTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class CharacterRelationNormalizationServiceTest extends Specification {

	private static final String RAW_RELATION_NAME = 'RAW_RELATION_NAME'
	private static final String RELATION_NAME = 'RELATION_NAME'

	private CharacterRelationSpouseNormalizationService characterRelationSpouseNormalizationServiceMock

	private CharacterRelationChildrenNormalizationService characterRelationChildrenNormalizationServiceMock

	private CharacterRelationRelativeNormalizationService characterRelationRelativeNormalizationServiceMock

	private CharacterRelationSiblingNormalizationService characterRelationSiblingNormalizationServiceMock

	private CharacterRelationFatherNormalizationService characterRelationFatherNormalizationServiceMock

	private CharacterRelationMotherNormalizationService characterRelationMotherNormalizationServiceMock

	private CharacterRelationNormalizationService characterRelationNormalizationService

	void setup() {
		characterRelationSpouseNormalizationServiceMock = Mock()
		characterRelationChildrenNormalizationServiceMock = Mock()
		characterRelationRelativeNormalizationServiceMock = Mock()
		characterRelationSiblingNormalizationServiceMock = Mock()
		characterRelationFatherNormalizationServiceMock = Mock()
		characterRelationMotherNormalizationServiceMock = Mock()
		characterRelationNormalizationService = new CharacterRelationNormalizationService(characterRelationSpouseNormalizationServiceMock,
				characterRelationChildrenNormalizationServiceMock, characterRelationRelativeNormalizationServiceMock,
				characterRelationSiblingNormalizationServiceMock, characterRelationFatherNormalizationServiceMock,
				characterRelationMotherNormalizationServiceMock)
	}

	void "when spouse parameter name is passed, CharacterRelationSpouseNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(CommonCharacterTemplateParameter.SPOUSE), RAW_RELATION_NAME)

		then:
		1 * characterRelationSpouseNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when children parameter name is passed, CharacterRelationChildrenNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(CommonCharacterTemplateParameter.CHILDREN), RAW_RELATION_NAME)

		then:
		1 * characterRelationChildrenNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when relative parameter name is passed, CharacterRelationRelativeNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(CommonCharacterTemplateParameter.RELATIVE), RAW_RELATION_NAME)

		then:
		1 * characterRelationRelativeNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when sibling parameter name is passed, CharacterRelationSiblingNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(IndividualTemplateParameter.SIBLING), RAW_RELATION_NAME)

		then:
		1 * characterRelationSiblingNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when father parameter name is passed, CharacterRelationFatherNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(IndividualTemplateParameter.FATHER), RAW_RELATION_NAME)

		then:
		1 * characterRelationFatherNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when mother parameter name is passed, CharacterRelationMotherNormalizationService is used"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(IndividualTemplateParameter.MOTHER), RAW_RELATION_NAME)

		then:
		1 * characterRelationMotherNormalizationServiceMock.normalize(RAW_RELATION_NAME) >> RELATION_NAME
		0 * _
		relationName == RELATION_NAME
	}

	void "when unknown parameter name is passed, null is returned"() {
		when:
		String relationName = characterRelationNormalizationService.normalize(keyOf(StringUtils.EMPTY), RAW_RELATION_NAME)

		then:
		0 * _
		relationName == null
	}

	private static CharacterRelationCacheKey keyOf(String parameterName) {
		CharacterRelationCacheKey.of(StringUtils.EMPTY, parameterName)
	}

}
