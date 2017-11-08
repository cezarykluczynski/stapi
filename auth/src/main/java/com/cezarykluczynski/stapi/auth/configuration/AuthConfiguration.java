package com.cezarykluczynski.stapi.auth.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiKeyProperties.class})
public class AuthConfiguration {
}
