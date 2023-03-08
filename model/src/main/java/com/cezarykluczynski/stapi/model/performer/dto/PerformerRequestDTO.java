package com.cezarykluczynski.stapi.model.performer.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class PerformerRequestDTO {

	private String uid;

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

	private Boolean audiobookPerformer;

	private Boolean cutPerformer;

	private Boolean disPerformer;

	private Boolean ds9Performer;

	private Boolean entPerformer;

	private Boolean filmPerformer;

	private Boolean ldPerformer;

	private Boolean picPerformer;

	private Boolean proPerformer;

	private Boolean puppeteer;

	private Boolean snwPerformer;

	private Boolean standInPerformer;

	private Boolean stPerformer;

	private Boolean stuntPerformer;

	private Boolean tasPerformer;

	private Boolean tngPerformer;

	private Boolean tosPerformer;

	private Boolean videoGamePerformer;

	private Boolean voicePerformer;

	private Boolean voyPerformer;

	private RequestSortDTO sort;

}
