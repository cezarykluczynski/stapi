package com.cezarykluczynski.stapi.etl.character.link.relation.dto;

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class CharacterPageLinkWithRelationName {

	private PageLink pageLink;

	private String relationName;

}
