package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;
import org.springframework.stereotype.Service;

@Service
class WordpressFactory {

	Wordpress createForUrl(String baseUrl) {
		return ClientFactory.fromConfig(ClientConfig.of(baseUrl, null, null, false));
	}

}
