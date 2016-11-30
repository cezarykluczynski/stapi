package com.cezarykluczynski.stapi.model.common.dto;


import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RealWorldPersonRequestDTO {

	private String guid;

	private String name;

	private String birthName;

	private Gender gender;

	private LocalDate dateOfBirthFrom;

	private LocalDate dateOfBirthTo;

	private LocalDate dateOfDeathFrom;

	private LocalDate dateOfDeathTo;

	private String placeOfBirth;

	private String placeOfDeath;

	private RequestOrderDTO order;

}
