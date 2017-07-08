package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class WordPressAfrozaarConnector {

	private final Wordpress wordpress;

	@Inject
	public WordPressAfrozaarConnector(WordPressSourcesProperties wordPressSourcesProperties, WordpressFactory wordpressFactory) {
		wordpress = wordpressFactory.createForUrl(wordPressSourcesProperties.getStarTrekCards().getApiUrl());
	}

	public PagedResponse<Page> getPage(String uri, Integer pageNumber, WordPressSource wordPressSource) {
		return wordpress.getPagedResponse(uri, Page.class, String.valueOf(pageNumber));
	}

}
