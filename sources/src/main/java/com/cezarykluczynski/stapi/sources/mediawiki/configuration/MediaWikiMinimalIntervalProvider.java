package com.cezarykluczynski.stapi.sources.mediawiki.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class MediaWikiMinimalIntervalProvider {

	@Getter
	private final Long memoryAlphaEnInterval;

	@Getter
	private final Long memoryBetaEnInterval;

	@Inject
	public MediaWikiMinimalIntervalProvider(MediaWikiSourcesProperties mediaWikiSourcesProperties,
		MediaWikiMinimalIntervalConfigurationStrategy mediaWikiMinimalIntervalConfigurationStrategy) {
		this.memoryAlphaEnInterval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(
				mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl(),
				mediaWikiSourcesProperties.getMemoryAlphaEn().getMinimalInterval());
		this.memoryBetaEnInterval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(
				mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl(),
				mediaWikiSourcesProperties.getMemoryBetaEn().getMinimalInterval());
	}

}
