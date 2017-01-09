package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.IntervalCalculationStrategy;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiMinimalIntervalProvider;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams;
import com.google.common.collect.Maps;
import info.bliki.api.Connector;
import info.bliki.api.User;
import info.bliki.api.query.RequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Map;

@Service
@Slf4j
public class BlikiConnector {

	private BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider;

	private Map<MediaWikiSource, Long> lastCallTimes = Maps.newHashMap();

	private Map<MediaWikiSource, String> names = Maps.newHashMap();

	private Map<MediaWikiSource, Boolean> logPostpones = Maps.newHashMap();

	private Map<MediaWikiSource, IntervalCalculationStrategy> intervalCalculationStrategies = Maps.newHashMap();

	private MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider;

	private MediaWikiSourcesProperties mediaWikiSourcesProperties;

	@Inject
	public BlikiConnector(BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider,
			MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider,
			MediaWikiSourcesProperties mediaWikiSourcesProperties) {
		this.mediaWikiMinimalIntervalProvider = mediaWikiMinimalIntervalProvider;
		this.blikiUserDecoratorBeanMapProvider = blikiUserDecoratorBeanMapProvider;
		this.mediaWikiSourcesProperties = mediaWikiSourcesProperties;
		configure();
	}

	private void configure() {
		lastCallTimes.put(MediaWikiSource.MEMORY_ALPHA_EN, 0L);
		lastCallTimes.put(MediaWikiSource.MEMORY_BETA_EN, 0L);
		names.put(MediaWikiSource.MEMORY_ALPHA_EN, "Memory Alpha (EN)");
		names.put(MediaWikiSource.MEMORY_BETA_EN, "Memory Beta (EN)");
		logPostpones.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties
				.getMemoryAlphaEn().getLogPostpones());
		logPostpones.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties
				.getMemoryBetaEn().getLogPostpones());
		intervalCalculationStrategies.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties
				.getMemoryAlphaEn().getIntervalCalculationStrategy());
		intervalCalculationStrategies.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties
				.getMemoryBetaEn().getIntervalCalculationStrategy());
	}

	@Cacheable(cacheNames = "pagesCache", condition = "@pageCacheService.isCacheable(#title, #mediaWikiSource)",
			key = "@pageCacheService.resolveKey(#title)", sync = true)
	public String getPage(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_ACTION, ApiParams.KEY_ACTION_VALUE_PARSE);
		params.put(ApiParams.KEY_PAGE, title);
		params.put(ApiParams.KEY_PROP, ApiParams.KEY_PROP_VALUE);
		return readXML(params, mediaWikiSource);
	}


	public String readXML(Map<String, String> params, MediaWikiSource mediaWikiSource) {
		RequestBuilder requestBuilder = RequestBuilder.create();
		requestBuilder.putAll(params);

		switch (mediaWikiSource) {
			case MEMORY_BETA_EN:
				return doQueryMemoryBeta(requestBuilder);
			default:
			case MEMORY_ALPHA_EN:
				return doQueryMemoryAlpha(requestBuilder);
		}
	}

	private synchronized String doQueryMemoryAlpha(RequestBuilder requestBuilder) {
		return synchronizedDoQuery(requestBuilder, MediaWikiSource.MEMORY_ALPHA_EN,
				mediaWikiMinimalIntervalProvider.getMemoryAlphaEnInterval());
	}

	private synchronized String doQueryMemoryBeta(RequestBuilder requestBuilder) {
		return synchronizedDoQuery(requestBuilder, MediaWikiSource.MEMORY_BETA_EN,
				mediaWikiMinimalIntervalProvider.getMemoryBetaEnInterval());
	}

	private String synchronizedDoQuery(RequestBuilder requestBuilder, MediaWikiSource mediaWikiSource, Long interval) {
		long startTime = System.currentTimeMillis();
		long diff = startTime - lastCallTimes.get(mediaWikiSource);
		long minimalInterval = interval;

		if (diff < minimalInterval) {
			long postpone = minimalInterval - diff;
			if (BooleanUtils.isTrue(logPostpones.get(mediaWikiSource))) {
				log.info("Postponing call to {} for another {} milliseconds", names.get(mediaWikiSource), postpone);
			}
			try {
				Thread.sleep(postpone);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		String xml = doQuery(requestBuilder, mediaWikiSource);
		// TODO: test it
		long lsstCallTime = IntervalCalculationStrategy.FROM_AFTER_RECEIVED
				.equals(intervalCalculationStrategies.get(mediaWikiSource)) ? System.currentTimeMillis() : startTime;
		lastCallTimes.put(mediaWikiSource, lsstCallTime);
		return xml;
	}

	private String doQuery(RequestBuilder requestBuilder, MediaWikiSource mediaWikiSource) {
		// TODO: remove private method call once this PR is merged and published in Maven Central
		// https://bitbucket.org/axelclk/info.bliki.wiki/pull-requests/10/connector-sendxml-should-be-public/diff
		try {
			UserDecorator userDecorator = blikiUserDecoratorBeanMapProvider.getUserEnumMap().get(mediaWikiSource);
			Connector connector = userDecorator.getConnector();
			Method method = connector.getClass().getDeclaredMethod("sendXML", User.class, RequestBuilder.class);
			method.setAccessible(true);
			return (String) method.invoke(connector, userDecorator, requestBuilder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
