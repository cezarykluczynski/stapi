package com.cezarykluczynski.stapi.etl.country.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class CountryDTO {

	public String name;

	public String code;

}
