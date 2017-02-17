package com.cezarykluczynski.stapi.sources.mediawiki.util.constant;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class CacheablePageTitles {

	public static final Map<MediaWikiSource, List<String>> SOURCES_TITLES = Maps.newHashMap();

	static {
		SOURCES_TITLES.put(MediaWikiSource.MEMORY_ALPHA_EN, Lists.newArrayList(
				PageTitle.STAR_TREK_GAME_PERFORMERS,
				PageTitle.MAJEL_BARRETT,
				PageTitle.MAJEL_BARRETT_RODDENBERRY,
				PageTitle.M_LEIGH_HUDEC,
				PageTitle.DAVID_KEITH_ANDERSON,
				PageTitle.DAVID_ANDERSON,
				PageTitle.SIDDIG_EL_FADIL,
				PageTitle.ALEXANDER_SIDDIG
		));
	}

}
