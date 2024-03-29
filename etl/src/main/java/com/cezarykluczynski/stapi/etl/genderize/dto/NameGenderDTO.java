package com.cezarykluczynski.stapi.etl.genderize.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameGenderDTO {

	private String name;

	private String gender;

	private Float probability;

}
