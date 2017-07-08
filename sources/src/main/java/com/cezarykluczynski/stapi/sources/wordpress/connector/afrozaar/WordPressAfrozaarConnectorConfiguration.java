package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WordPressSourcesProperties.class})
public class WordPressAfrozaarConnectorConfiguration {
}
