package com.cezarykluczynski.stapi.model.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = HibernateProperties.PREFIX)
public class HibernateProperties {

	public static final String PREFIX = "hibernate";

	private String dialect;
	private String defaultSchema;

}
