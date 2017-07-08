package com.cezarykluczynski.stapi.sources.wordpress.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = WordPressSourcesProperties.PREFIX)
public class WordPressSourcesProperties {

	public static final String PREFIX = "source.wordPress";

	private WordPressSourceProperties starTrekCards;

}
