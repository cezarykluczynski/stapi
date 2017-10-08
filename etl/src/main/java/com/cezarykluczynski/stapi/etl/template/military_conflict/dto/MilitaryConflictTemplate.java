package com.cezarykluczynski.stapi.etl.template.military_conflict.dto;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class MilitaryConflictTemplate {

	private String name;

	private Page page;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean earthConflict;

	private Boolean federationWar;

	private Boolean klingonWar;

	private Boolean dominionWarBattle;

	private Boolean alternateReality;

	private Set<Location> locations = Sets.newHashSet();

	private Set<Organization> firstSideBelligerents = Sets.newHashSet();

	private Set<Organization> secondSideBelligerents = Sets.newHashSet();

	private Set<Character> firstSideCommanders = Sets.newHashSet();

	private Set<Character> secondSideCommanders = Sets.newHashSet();

}
