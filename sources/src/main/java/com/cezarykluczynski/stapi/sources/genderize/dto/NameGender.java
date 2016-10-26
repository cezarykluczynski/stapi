package com.cezarykluczynski.stapi.sources.genderize.dto;

import lombok.Data;

@Data
public class NameGender {

	private String name;

	private String gender;

	private Float probability;

}
