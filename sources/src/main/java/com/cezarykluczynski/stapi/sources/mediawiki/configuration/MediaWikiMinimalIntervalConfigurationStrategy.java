package com.cezarykluczynski.stapi.sources.mediawiki.configuration;

import com.cezarykluczynski.stapi.sources.mediawiki.service.fandom.FandomUrlDetector;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MediaWikiMinimalIntervalConfigurationStrategy {

	private static final Long FANDOM_INTERVAL = 1000L;

	private final FandomUrlDetector fandomUrlDetector;

	public MediaWikiMinimalIntervalConfigurationStrategy(FandomUrlDetector fandomUrlDetector) {
		this.fandomUrlDetector = fandomUrlDetector;
	}

	public Long configureInterval(String apiUrl, String minimalInterval) {
		Preconditions.checkNotNull(apiUrl, "MediaWiki API URL cannot be null");

		boolean isFandom = fandomUrlDetector.isFandomWikiUrl(apiUrl);

		if (minimalInterval == null || "auto".equals(minimalInterval.toLowerCase())) {
			// It's safe to assume that requests to Fandom should not be more frequent than 1 per second
			return isFandom ? FANDOM_INTERVAL : 0L;
		}

		try {
			Long interval = Long.parseLong(minimalInterval);

			if (isFandom && interval < FANDOM_INTERVAL) {
				log.warn("Setting interval for less than 1000 milliseconds when using Fandom's wiki is not recommended");
			}

			return interval;
		} catch (NumberFormatException e) {
			throw new BeanInitializationException(String.format("Minimal interval for %s should be either \"auto\" or a number of milliseconds, "
					+ "but %s given.", apiUrl, minimalInterval), e);
		}
	}

}
