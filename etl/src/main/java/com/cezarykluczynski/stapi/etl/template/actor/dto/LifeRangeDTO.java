package com.cezarykluczynski.stapi.etl.template.actor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LifeRangeDTO {

	private LocalDate dateOfBirth;

	private String placeOfBirth;

	private LocalDate dateOfDeath;

	private String placeOfDeath;

}
