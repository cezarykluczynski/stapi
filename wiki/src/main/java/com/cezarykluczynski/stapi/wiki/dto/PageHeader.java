package com.cezarykluczynski.stapi.wiki.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageHeader {

	private Long pageId;

	private String title;

}
