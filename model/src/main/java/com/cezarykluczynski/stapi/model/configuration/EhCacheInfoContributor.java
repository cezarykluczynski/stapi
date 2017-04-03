package com.cezarykluczynski.stapi.model.configuration;

import com.google.common.collect.Maps;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EhCacheInfoContributor implements InfoContributor {

	@Override
	public void contribute(Info.Builder builder) {
		CacheManager cacheManager = CacheManager.getInstance();
		String[] cacheNames = cacheManager.getCacheNames();
		Map<String, String> map = Maps.newHashMap();

		for (int i = 0; i < cacheNames.length; i++) {
			String cacheName = cacheNames[i];
			Cache cache = cacheManager.getCache(cacheName);
			map.put(cacheName, cache.getStatistics().toString()); // TODO
		}

		builder.withDetail("ehcache", map);
	}

}
