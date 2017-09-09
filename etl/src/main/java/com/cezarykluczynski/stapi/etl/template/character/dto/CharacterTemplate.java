package com.cezarykluczynski.stapi.etl.template.character.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
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

	private Set<Character> creators = Sets.newHashSet();

}
