package com.cezarykluczynski.stapi.sources.wordpress.configuration;

import lombok.Data;

@Data
public class WordPressSourceProperties {

	private String apiUrl;

	private Long minimalInterval;

}
