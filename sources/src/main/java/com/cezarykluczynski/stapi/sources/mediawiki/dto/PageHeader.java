package com.cezarykluczynski.stapi.sources.mediawiki.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PageHeader {

	private Long pageId;

	private String title;

}
