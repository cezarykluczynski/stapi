package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.IntervalCalculationStrategy;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiMinimalIntervalProvider;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaWikisDetector;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import info.bliki.api.Connector;
import info.bliki.api.User;
import info.bliki.api.query.RequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BlikiConnector {

	private static final List<Long> NETWORK_TROUBLE_POSTPONES_TIMES = Lists.newArrayList(10000L, 30000L, 60000L, 300000L);

	private Map<MediaWikiSource, Long> lastCallTimes = Maps.newHashMap();

	private Map<MediaWikiSource, String> names = Maps.newHashMap();

	private Map<MediaWikiSource, Boolean> logPostpones = Maps.newHashMap();

	private Map<MediaWikiSource, IntervalCalculationStrategy> intervalCalculationStrategies = Maps.newHashMap();

	private Integer lastNetworkTroublePostponeIndex = 0;

	private final BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider;

	private final WikiaWikisDetector wikiaWikisDetector;

	private final MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider;

	private final MediaWikiSourcesProperties mediaWikiSourcesProperties;

	public BlikiConnector(BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider, WikiaWikisDetector wikiaWikisDetector,
			MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider, MediaWikiSourcesProperties mediaWikiSourcesProperties) {
		this.blikiUserDecoratorBeanMapProvider = blikiUserDecoratorBeanMapProvider;
		this.wikiaWikisDetector = wikiaWikisDetector;
		this.mediaWikiMinimalIntervalProvider = mediaWikiMinimalIntervalProvider;
		this.mediaWikiSourcesProperties = mediaWikiSourcesProperties;
		configure();
	}

	private void configure() {
		lastCallTimes.put(MediaWikiSource.MEMORY_ALPHA_EN, 0L);
		lastCallTimes.put(MediaWikiSource.MEMORY_BETA_EN, 0L);
		names.put(MediaWikiSource.MEMORY_ALPHA_EN, "Memory Alpha (EN)");
		names.put(MediaWikiSource.MEMORY_BETA_EN, "Memory Beta (EN)");
		logPostpones.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties.getMemoryAlphaEn().getLogPostpones());
		logPostpones.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties.getMemoryBetaEn().getLogPostpones());
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
		params.put(ApiParams.KEY_PROP, resolveProperties(mediaWikiSource));
		return readXML(params, mediaWikiSource);
	}

	public String getPageInfo(String title, MediaWikiSource mediaWikiSource) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_ACTION, ApiParams.KEY_ACTION_VALUE_QUERY);
		params.put(ApiParams.KEY_TITLES, title);
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
		return synchronizedDoQuery(requestBuilder, MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiMinimalIntervalProvider.getMemoryAlphaEnInterval());
	}

	private synchronized String doQueryMemoryBeta(RequestBuilder requestBuilder) {
		return synchronizedDoQuery(requestBuilder, MediaWikiSource.MEMORY_BETA_EN, mediaWikiMinimalIntervalProvider.getMemoryBetaEnInterval());
	}

	@SuppressFBWarnings("SWL_SLEEP_WITH_LOCK_HELD")
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

		long lastCallTime = IntervalCalculationStrategy.FROM_AFTER_RECEIVED
				.equals(intervalCalculationStrategies.get(mediaWikiSource)) ? System.currentTimeMillis() : startTime;
		lastCallTimes.put(mediaWikiSource, lastCallTime);

		synchronized (this) {
			if (xml == null) {
				long postpone = getNetworkTroublePostpone();
				log.info("Network troubles. Postponing next call for {} seconds", postpone / 1000);
				try {
					Thread.sleep(postpone);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				return synchronizedDoQuery(requestBuilder, mediaWikiSource, interval);
			} else {
				if (lastNetworkTroublePostponeIndex > 0) {
					log.info("Network is back to normal");
				}
				lastNetworkTroublePostponeIndex = 0;
			}
		}

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
			throw new StapiRuntimeException(e);
		}
	}

	private String resolveProperties(MediaWikiSource mediaWikiSource) {
		return wikiaWikisDetector.isWikiaWiki(mediaWikiSource) ? ApiParams.KEY_PROP_VALUE : ApiParams.KEY_PROP_VALUE_MW_1_26_UP;
	}

	private long getNetworkTroublePostpone() {
		long networkTroublePostpone = NETWORK_TROUBLE_POSTPONES_TIMES.get(lastNetworkTroublePostponeIndex);
		lastNetworkTroublePostponeIndex = Math.min(lastNetworkTroublePostponeIndex + 1, NETWORK_TROUBLE_POSTPONES_TIMES.size() - 1);
		return networkTroublePostpone;
	}

}
