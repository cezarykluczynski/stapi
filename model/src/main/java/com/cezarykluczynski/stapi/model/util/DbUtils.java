package com.cezarykluczynski.stapi.model.util;

import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DbUtils {

	public int extractPortFromJdbcUrl(String jdbcUrl) {
		if (!jdbcUrl.startsWith("jdbc:")) {
			return -1;
		}
		return URI.create(jdbcUrl.substring(5)).getPort();
	}

}
