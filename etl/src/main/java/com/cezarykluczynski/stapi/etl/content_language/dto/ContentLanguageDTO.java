package com.cezarykluczynski.stapi.etl.content_language.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ContentLanguageDTO {

	private String name;

	private String code;

}
