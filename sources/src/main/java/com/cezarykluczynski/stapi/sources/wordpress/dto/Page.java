package com.cezarykluczynski.stapi.sources.wordpress.dto;

import lombok.Data;

@Data
public class Page {

	private String slug;

	private Long id;

	private String rawContent;

	private String renderedContent;

}
