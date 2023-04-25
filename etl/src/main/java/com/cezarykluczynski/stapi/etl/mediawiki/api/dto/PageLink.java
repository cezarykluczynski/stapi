package com.cezarykluczynski.stapi.etl.mediawiki.api.dto;

import lombok.Data;

@Data
public class PageLink {

	private String title;

	private String description;

	private String untrimmedDescription;

	private Integer startPosition;

	private Integer endPosition;

}
