package com.cezarykluczynski.stapi.etl.mediawiki.api.dto;

import lombok.Data;

@Data
public class PageSection {

	private Integer level;

	private String text;

	private String anchor;

	private String number;

	private Integer byteOffset;

	private String wikitext;

}
