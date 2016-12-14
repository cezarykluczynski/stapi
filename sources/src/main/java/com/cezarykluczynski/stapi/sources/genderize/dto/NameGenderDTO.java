package com.cezarykluczynski.stapi.sources.genderize.dto;

import lombok.Data;

@Data
public class NameGenderDTO {

	private String name;

	private String gender;

	private Float probability;

}
