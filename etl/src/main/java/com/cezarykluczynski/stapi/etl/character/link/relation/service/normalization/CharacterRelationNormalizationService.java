package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.template.character.dto.CommonCharacterTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import org.springframework.stereotype.Service;

@Service
public class CharacterRelationNormalizationService {

	private final CharacterRelationSpouseNormalizationService characterRelationSpouseNormalizationService;

	private final CharacterRelationChildrenNormalizationService characterRelationChildrenNormalizationService;

	private final CharacterRelationRelativeNormalizationService characterRelationRelativeNormalizationService;

	private final CharacterRelationSiblingNormalizationService characterRelationSiblingNormalizationService;

	private final CharacterRelationFatherNormalizationService characterRelationFatherNormalizationService;

	private final CharacterRelationMotherNormalizationService characterRelationMotherNormalizationService;

	public CharacterRelationNormalizationService(CharacterRelationSpouseNormalizationService characterRelationSpouseNormalizationService,
			CharacterRelationChildrenNormalizationService characterRelationChildrenNormalizationService,
			CharacterRelationRelativeNormalizationService characterRelationRelativeNormalizationService,
			CharacterRelationSiblingNormalizationService characterRelationSiblingNormalizationService,
			CharacterRelationFatherNormalizationService characterRelationFatherNormalizationService,
			CharacterRelationMotherNormalizationService characterRelationMotherNormalizationService) {
		this.characterRelationSpouseNormalizationService = characterRelationSpouseNormalizationService;
		this.characterRelationChildrenNormalizationService = characterRelationChildrenNormalizationService;
		this.characterRelationRelativeNormalizationService = characterRelationRelativeNormalizationService;
		this.characterRelationSiblingNormalizationService = characterRelationSiblingNormalizationService;
		this.characterRelationFatherNormalizationService = characterRelationFatherNormalizationService;
		this.characterRelationMotherNormalizationService = characterRelationMotherNormalizationService;
	}

	@SuppressWarnings("ReturnCount")
	public String normalize(CharacterRelationCacheKey characterRelationCacheKey, String rawRelationName) {
		switch (characterRelationCacheKey.getParameterName()) {
			case CommonCharacterTemplateParameter.SPOUSE:
				return characterRelationSpouseNormalizationService.normalize(rawRelationName);
			case CommonCharacterTemplateParameter.CHILDREN:
				return characterRelationChildrenNormalizationService.normalize(rawRelationName);
			case CommonCharacterTemplateParameter.RELATIVE:
			return characterRelationRelativeNormalizationService.normalize(rawRelationName);
			case IndividualTemplateParameter.SIBLING:
			return characterRelationSiblingNormalizationService.normalize(rawRelationName);
			case IndividualTemplateParameter.FATHER:
				return characterRelationFatherNormalizationService.normalize(rawRelationName);
			case IndividualTemplateParameter.MOTHER:
				return characterRelationMotherNormalizationService.normalize(rawRelationName);
			default:
				return null;
		}
	}

}
