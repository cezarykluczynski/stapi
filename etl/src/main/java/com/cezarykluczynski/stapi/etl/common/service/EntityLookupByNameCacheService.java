package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EntityLookupByNameCacheService {

	public String resolveKey(String entityType, String pageTitle, MediaWikiSource mediaWikiSource) {
		return String.format("%s:%s:%s", entityType, StringUtils.substringBefore(pageTitle, '#'), mediaWikiSource);
	}

}
