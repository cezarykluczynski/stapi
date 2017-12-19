package com.cezarykluczynski.stapi.model.configuration;

import com.cezarykluczynski.stapi.util.constant.Package;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.statistics.StatisticsGateway;
import net.sf.ehcache.statistics.extended.ExtendedStatistics;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.List;
import java.util.Map;

public class EhCacheInfoContributor implements InfoContributor {

	private static final String DOT = ".";

	private final CacheManager cacheManager;

	EhCacheInfoContributor(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void contribute(Info.Builder builder) {
		String[] cacheNames = cacheManager.getCacheNames();
		Map<String, Integer> map = Maps.newHashMap();

		for (int i = 0; i < cacheNames.length; i++) {
			String cacheName = cacheNames[i];

			if (!cacheName.startsWith(Package.ROOT)) {
				continue;
			}

			Cache cache = cacheManager.getCache(cacheName);
			StatisticsGateway statisticsGateway = cache.getStatistics();
			ExtendedStatistics extendedStatistics = statisticsGateway.getExtended();
			String summarizedCacheName = getSummarizedCacheName(cacheName);
			map.putIfAbsent(summarizedCacheName, 0);
			map.put(summarizedCacheName, map.get(summarizedCacheName) + extendedStatistics.allPut().count().value().intValue());
		}

		builder.withDetail("ehcache", map);
	}

	private String getSummarizedCacheName(String cacheName) {
		List<String> packageParts = Lists.newArrayList(StringUtils.split(cacheName, DOT));
		int entityPosition = packageParts.indexOf("entity");
		return StringUtils.join(packageParts.subList(0, entityPosition), DOT);
	}

}
