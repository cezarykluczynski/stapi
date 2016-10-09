package com.cezarykluczynski.stapi.wiki.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class PageHeader {

	private Long pageId;

	private String title;

}
