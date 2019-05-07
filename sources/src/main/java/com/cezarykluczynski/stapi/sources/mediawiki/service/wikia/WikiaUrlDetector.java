package com.cezarykluczynski.stapi.sources.mediawiki.service.wikia;

import org.springframework.stereotype.Service;

@Service
public class WikiaUrlDetector {

	public boolean isWikiaWikiUrl(String url) {
		return url.contains(".fandom.com");
	}

}
