package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CharacterRelationsSidebarTemplateMappingsProvider {

	private static final Map<CharacterRelationCacheKey, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.FATHER), CharacterRelationName.FATHER);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.MOTHER), CharacterRelationName.MOTHER);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.OWNER), CharacterRelationName.OWNER);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.SIBLING), CharacterRelationName.SIBLING);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.RELATIVE), CharacterRelationName.RELATIVE);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.SPOUSE), CharacterRelationName.SPOUSE);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_INDIVIDUAL, IndividualTemplateParameter.CHILDREN), CharacterRelationName.CHILD);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_HOLOGRAM, HologramTemplateParameter.CREATOR), CharacterRelationName.CREATOR);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_HOLOGRAM, HologramTemplateParameter.SPOUSE), CharacterRelationName.SPOUSE);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_HOLOGRAM, HologramTemplateParameter.CHILDREN), CharacterRelationName.CHILD);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_HOLOGRAM, HologramTemplateParameter.RELATIVE), CharacterRelationName.RELATIVE);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_FICTIONAL, FictionalTemplateParameter.CREATOR), CharacterRelationName.CREATOR);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_FICTIONAL, FictionalTemplateParameter.CHARACTER), CharacterRelationName.ORIGINAL_CHARACTER);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_FICTIONAL, FictionalTemplateParameter.SPOUSE), CharacterRelationName.SPOUSE);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_FICTIONAL, FictionalTemplateParameter.CHILDREN), CharacterRelationName.CHILD);
		MAPPINGS.put(keyOf(TemplateTitle.SIDEBAR_FICTIONAL, FictionalTemplateParameter.RELATIVE), CharacterRelationName.RELATIVE);
	}

	public String provideFor(CharacterRelationCacheKey characterRelationCacheKey) {
		return MAPPINGS.get(characterRelationCacheKey);
	}

	private static CharacterRelationCacheKey keyOf(String sidebarTemplateTitle, String parameterName) {
		return CharacterRelationCacheKey.of(sidebarTemplateTitle, parameterName);
	}

}
