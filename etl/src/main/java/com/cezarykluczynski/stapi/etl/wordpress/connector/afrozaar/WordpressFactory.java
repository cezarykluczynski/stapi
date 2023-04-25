package com.cezarykluczynski.stapi.etl.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import org.springframework.stereotype.Service;

@Service
class WordpressFactory {

	Wordpress createForUrl(String baseUrl) {
		return ClientFactory.fromConfig(ClientConfig.of(baseUrl, null, null, false, false));
	}

}
