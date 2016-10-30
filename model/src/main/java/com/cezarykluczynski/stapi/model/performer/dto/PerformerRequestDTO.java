package com.cezarykluczynski.stapi.model.performer.dto;

import com.cezarykluczynski.stapi.model.common.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PerformerRequestDTO {

	private String name;

	private String birthName;

	private Gender gender;

	private LocalDate dateOfBirthFrom;

	private LocalDate dateOfBirthTo;

	private LocalDate dateOfDeathFrom;

	private LocalDate dateOfDeathTo;

	private String placeOfBirth;

	private String placeOfDeath;

	private Boolean animalPerformer;

	private Boolean disPerformer;

	private Boolean ds9Performer;

	private Boolean entPerformer;

	private Boolean filmPerformer;

	private Boolean standInPerformer;

	private Boolean stuntPerformer;

	private Boolean tasPerformer;

	private Boolean tngPerformer;

	private Boolean tosPerformer;

	private Boolean videoGamePerformer;

	private Boolean voicePerformer;

	private Boolean voyPerformer;

}
