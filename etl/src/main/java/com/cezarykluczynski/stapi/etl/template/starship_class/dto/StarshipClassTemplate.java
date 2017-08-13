package com.cezarykluczynski.stapi.etl.template.starship_class.dto;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import lombok.Data;

@Data
public class StarshipClassTemplate {

	private String name;

	private Page page;

	private Species affiliatedSpecies;

	private Organization affiliatedOrganization;

	private SpacecraftType spacecraftType;

	private Integer numberOfDecks;

	private Boolean warpCapable;

	private String activeFrom;

	private String activeTo;

}
