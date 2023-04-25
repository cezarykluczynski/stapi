package com.cezarykluczynski.stapi.etl.mediawiki.dto;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PageHeader {

	private Long pageId;

	private String title;

	private MediaWikiSource mediaWikiSource;

}
