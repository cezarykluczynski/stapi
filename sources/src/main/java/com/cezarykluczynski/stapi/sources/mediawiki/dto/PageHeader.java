package com.cezarykluczynski.stapi.sources.mediawiki.dto;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PageHeader {

	private Long pageId;

	private String title;

	private MediaWikiSource mediaWikiSource;

}
