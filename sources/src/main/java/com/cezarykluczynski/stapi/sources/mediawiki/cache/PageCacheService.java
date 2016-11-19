package com.cezarykluczynski.stapi.sources.mediawiki.cache;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.CacheablePageNames;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PageCacheService {

	public boolean isCacheable(String title, MediaWikiSource mediaWikiSource) {
		if (!CacheablePageNames.SOURCES_TITLES.containsKey(mediaWikiSource)) {
			return false;
		}

		return CacheablePageNames.SOURCES_TITLES.get(mediaWikiSource).contains(getCanonicalTitle(title));
	}

	public String resolveKey(String title) {
		return getCanonicalTitle(title);
	}

	private String getCanonicalTitle(String title) {
		return StringUtils.substringBefore(title, "#");
	}

}
