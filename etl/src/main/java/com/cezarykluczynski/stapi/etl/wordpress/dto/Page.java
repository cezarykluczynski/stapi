package com.cezarykluczynski.stapi.etl.wordpress.dto;

import lombok.Data;

@Data
public class Page {

	private String slug;

	private Long id;

	private String rawTitle;

	private String renderedTitle;

	private String rawContent;

	private String renderedContent;

}
