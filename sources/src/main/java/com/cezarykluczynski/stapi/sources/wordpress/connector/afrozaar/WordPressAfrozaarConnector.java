package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties;
import com.cezarykluczynski.stapi.sources.wordpress.service.WordPressSourceConfigurationProvider;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

@Service
@Slf4j
public class WordPressAfrozaarConnector {

	private static final String PAGE_WITH_SLUG = "/pages?parent={parent}&page={page}";
	private static final int RETRY_COUNT = 5;

	private final Map<WordPressSource, Long> lastCallTimes = Maps.newHashMap();

	private final Wordpress wordpress;

	private final WordPressSourceConfigurationProvider wordPressSourceConfigurationProvider;

	public WordPressAfrozaarConnector(WordPressSourcesProperties wordPressSourcesProperties, WordpressFactory wordpressFactory,
			WordPressSourceConfigurationProvider wordPressSourceConfigurationProvider) {
		wordpress = wordpressFactory.createForUrl(wordPressSourcesProperties.getStarTrekCards().getApiUrl());
		this.wordPressSourceConfigurationProvider = wordPressSourceConfigurationProvider;
		configure();
	}

	private void configure() {
		lastCallTimes.put(WordPressSource.STAR_TREK_CARDS, 0L);
	}

	public PagedResponse<Page> getPagesUnderPage(String pageId, Integer pageNumber, WordPressSource wordPressSource) {
		return privateGetPagesUnderPage(pageId, pageNumber, wordPressSource, 1);
	}

	private PagedResponse<Page> privateGetPagesUnderPage(String pageId, Integer pageNumber, WordPressSource wordPressSource, int tryNumber) {
		long interval = wordPressSourceConfigurationProvider.provideFor(wordPressSource).getMinimalInterval();
		long startTime = System.currentTimeMillis();
		long diff = startTime - lastCallTimes.get(wordPressSource);

		if (diff < interval) {
			try {
				Thread.sleep(interval - diff);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		PagedResponse<Page> pagedResponse;

		try {
			pagedResponse = wordpress.getPagedResponse(PAGE_WITH_SLUG, Page.class, pageId, String.valueOf(pageNumber));
			lastCallTimes.put(wordPressSource, System.currentTimeMillis());
		} catch (HttpMessageNotReadableException | HttpServerErrorException | ResourceAccessException e) {
			lastCallTimes.put(wordPressSource, System.currentTimeMillis());
			if (tryNumber > RETRY_COUNT) {
				throw e;
			} else {
				return privateGetPagesUnderPage(pageId, pageNumber, wordPressSource, tryNumber + 1);
			}
		}

		return pagedResponse;
	}


}
