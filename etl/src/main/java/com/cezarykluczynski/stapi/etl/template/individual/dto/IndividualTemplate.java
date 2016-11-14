package com.cezarykluczynski.stapi.etl.template.individual.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.model.common.entity.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus;
import lombok.Data;

@Data
public class IndividualTemplate {

	private String name;

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

}
