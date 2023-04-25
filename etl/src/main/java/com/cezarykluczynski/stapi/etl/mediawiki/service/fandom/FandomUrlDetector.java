package com.cezarykluczynski.stapi.etl.mediawiki.service.fandom;

import org.springframework.stereotype.Service;

@Service
public class FandomUrlDetector {

	public boolean isFandomWikiUrl(String url) {
		return url.contains(".fandom.com");
	}

}
