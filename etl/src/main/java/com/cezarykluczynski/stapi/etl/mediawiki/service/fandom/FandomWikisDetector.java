package com.cezarykluczynski.stapi.etl.mediawiki.service.fandom;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourcesProperties;
import com.google.common.collect.Maps;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FandomWikisDetector {

	private Map<MediaWikiSource, Boolean> areFandomWikis = Maps.newHashMap();

	private final MediaWikiSourcesProperties mediaWikiSourcesProperties;

	private final FandomUrlDetector fandomUrlDetector;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public FandomWikisDetector(MediaWikiSourcesProperties mediaWikiSourcesProperties, FandomUrlDetector fandomUrlDetector) {
		this.mediaWikiSourcesProperties = mediaWikiSourcesProperties;
		this.fandomUrlDetector = fandomUrlDetector;
		configure();
	}

	private void configure() {
		areFandomWikis.put(MediaWikiSource.MEMORY_ALPHA_EN, fandomUrlDetector
				.isFandomWikiUrl(mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl()));
		areFandomWikis.put(MediaWikiSource.MEMORY_BETA_EN, fandomUrlDetector
				.isFandomWikiUrl(mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl()));
	}

	public boolean isFandomWiki(MediaWikiSource mediaWikiSource) {
		return areFandomWikis.containsKey(mediaWikiSource) && areFandomWikis.get(mediaWikiSource);
	}

}
