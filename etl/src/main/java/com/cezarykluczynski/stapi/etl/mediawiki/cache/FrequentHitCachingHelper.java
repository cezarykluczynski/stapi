package com.cezarykluczynski.stapi.etl.mediawiki.cache;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FrequentHitCachingHelper {

	public static final int CACHE_THRESHOLD = 2;

	private Map<MediaWikiSource, Map<String, Integer>> cacheMap;

	public FrequentHitCachingHelper() {
		createNewMap();
	}

	public synchronized boolean isCacheable(String title, MediaWikiSource mediaWikiSource) {
		cacheMap.get(mediaWikiSource).putIfAbsent(title, 0);
		int updatedHitCount = cacheMap.get(mediaWikiSource).get(title) + 1;
		cacheMap.get(mediaWikiSource).put(title, updatedHitCount);
		return updatedHitCount >= CACHE_THRESHOLD;
	}

	public Map<MediaWikiSource, Map<String, Integer>> dumpStatisticsAndReset() {
		synchronized (this) {
			Map<MediaWikiSource, Map<String, Integer>> mapClone = Maps.newHashMap();
			cacheMap.forEach((mediaWikiSource, stringIntegerMap) -> {
				if (!stringIntegerMap.isEmpty()) {
					mapClone.put(mediaWikiSource, Map.copyOf(stringIntegerMap));
				}
			});
			createNewMap();
			return mapClone;
		}
	}

	private void createNewMap() {
		cacheMap = Maps.newHashMap();
		cacheMap.put(MediaWikiSource.MEMORY_ALPHA_EN, Maps.newHashMap());
		cacheMap.put(MediaWikiSource.MEMORY_BETA_EN, Maps.newHashMap());
	}

}
