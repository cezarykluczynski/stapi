package com.cezarykluczynski.stapi.sources.mediawiki.api.dto;

import lombok.Data;

@Data
public class PageLink {

	private String title;

	private String description;

	private Integer startPosition;

	private Integer endPosition;

}
