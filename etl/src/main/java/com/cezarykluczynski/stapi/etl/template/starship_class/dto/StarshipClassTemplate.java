package com.cezarykluczynski.stapi.etl.template.starship_class.dto;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class StarshipClassTemplate {

	private String name;

	private Page page;

	private Integer numberOfDecks;

	private String crew;

	private Boolean mirror;

	private Boolean alternateReality;

	private Boolean warpCapable;

	private String activeFrom;

	private String activeTo;

	private Species species;

	private Set<Organization> owners = Sets.newHashSet();

	private Set<Organization> operators = Sets.newHashSet();

	private Set<Organization> affiliations = Sets.newHashSet();

	private Set<Weapon> armaments = Sets.newHashSet();

	private Set<SpacecraftType> spacecraftTypes = Sets.newHashSet();

}
