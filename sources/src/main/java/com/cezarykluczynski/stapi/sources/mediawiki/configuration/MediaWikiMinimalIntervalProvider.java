package com.cezarykluczynski.stapi.sources.mediawiki.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class MediaWikiMinimalIntervalProvider {

	@Getter
	private Long memoryAlphaEnInterval;

	@Getter
	private Long memoryBetaEnInterval;

	@Inject
	public MediaWikiMinimalIntervalProvider(MediaWikiSourcesProperties mediaWikiSourcesProperties,
		MediaWikiMinimalIntervalConfigurationStrategy mediaWikiMinimalIntervalConfigurationStrategy) {
		this.memoryAlphaEnInterval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(
				mediaWikiSourcesProperties.getMemoryAlphaEnApiUrl(),
				mediaWikiSourcesProperties.getMemoryAlphaEnMinimalInterval());
		this.memoryBetaEnInterval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(
				mediaWikiSourcesProperties.getMemoryBetaEnApiUrl(),
				mediaWikiSourcesProperties.getMemoryBetaEnMinimalInterval());
	}

}
