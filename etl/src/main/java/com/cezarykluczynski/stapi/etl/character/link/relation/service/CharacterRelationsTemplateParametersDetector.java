package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CharacterRelationsTemplateParametersDetector {

	private static final Set<String> SIDEBAR_INDIVIDUAL_RELATIONS_KEYS = Sets.newHashSet(IndividualTemplateParameter.FATHER,
			IndividualTemplateParameter.MOTHER, IndividualTemplateParameter.OWNER, IndividualTemplateParameter.SIBLING,
			IndividualTemplateParameter.RELATIVE, IndividualTemplateParameter.SPOUSE, IndividualTemplateParameter.CHILDREN);

	private static final Set<String> SIDEBAR_HOLOGRAM_RELATIONS_KEYS = Sets.newHashSet(HologramTemplateParameter.CREATOR,
			HologramTemplateParameter.SPOUSE, HologramTemplateParameter.CHILDREN, HologramTemplateParameter.RELATIVE);

	private static final Set<String> SIDEBAR_FICTIONAL_RELATIONS_KEYS = Sets.newHashSet(FictionalTemplateParameter.CREATOR,
			FictionalTemplateParameter.CHARACTER, FictionalTemplateParameter.SPOUSE, FictionalTemplateParameter.CHILDREN,
			FictionalTemplateParameter.RELATIVE);

	public boolean isSidebarIndividualPartKey(String key) {
		return SIDEBAR_INDIVIDUAL_RELATIONS_KEYS.contains(key);
	}

	public boolean isSidebarHologramPartKey(String key) {
		return SIDEBAR_HOLOGRAM_RELATIONS_KEYS.contains(key);
	}

	public boolean isSidebarFictionalPartKey(String key) {
		return SIDEBAR_FICTIONAL_RELATIONS_KEYS.contains(key);
	}

}
