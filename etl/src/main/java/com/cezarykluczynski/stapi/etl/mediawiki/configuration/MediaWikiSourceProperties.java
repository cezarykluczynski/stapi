package com.cezarykluczynski.stapi.etl.mediawiki.configuration;

import lombok.Data;

@Data
public class MediaWikiSourceProperties {

	private String apiUrl;

	private String minimalInterval;

	private Boolean logPostpones;

	private IntervalCalculationStrategy intervalCalculationStrategy;

}
