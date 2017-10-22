package com.cezarykluczynski.stapi.etl.template.character.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class CharacterTemplate {

	private String name;

	private Page page;

	private Gender gender;

	private Boolean deceased;

	private Integer yearOfBirth;

	private Integer monthOfBirth;

	private Integer dayOfBirth;

	private String placeOfBirth;

	private Integer yearOfDeath;

	private Integer monthOfDeath;

	private Integer dayOfDeath;

	private String placeOfDeath;

	private Integer height;

	private Integer weight;

	private BloodType bloodType;

	private MaritalStatus maritalStatus;

	private String serialNumber;

	private String hologramActivationDate;

	private String hologramStatus;

	private String hologramDateStatus;

	private Boolean hologram;

	private Boolean fictionalCharacter;

	private Boolean mirror;

	private Boolean alternateReality;

	private Set<Performer> performers = Sets.newHashSet();

	private Set<CharacterSpecies> characterSpecies = Sets.newHashSet();

	private Set<CharacterRelation> characterRelations = Sets.newHashSet();

	private Set<Title> titles = Sets.newHashSet();

	private Set<Occupation> occupations = Sets.newHashSet();

	private Set<Organization> organizations = Sets.newHashSet();

}
