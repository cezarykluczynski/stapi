package com.cezarykluczynski.stapi.etl.character.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class CharacterRelationCacheKey {

	private String sidebarTemplateTitle;

	private String parameterName;

}
