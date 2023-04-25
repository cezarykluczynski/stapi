package com.cezarykluczynski.stapi.etl.wordpress.connector.afrozaar;

import com.cezarykluczynski.stapi.etl.wordpress.configuration.WordPressSourcesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WordPressSourcesProperties.class})
public class WordPressAfrozaarConnectorConfiguration {
}
