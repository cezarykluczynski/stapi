package com.cezarykluczynski.stapi.etl.template.individual.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class IndividualTemplate {

	private String name;

	private Page page;

	private Gender gender;

	private Boolean deceased;

	private Integer statusDate;

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

	private Boolean mirror;

	private Boolean alternateReality;

	private boolean productOfRedirect;

	private Set<Performer> performers = Sets.newHashSet();

}
