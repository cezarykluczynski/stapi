package com.cezarykluczynski.stapi.sources.mediawiki.util.constant;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.util.constant.PageName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class CacheablePageNames {

	public static final Map<MediaWikiSource, List<String>> SOURCES_TITLES = Maps.newHashMap();

	static {
		SOURCES_TITLES.put(MediaWikiSource.MEMORY_ALPHA_EN, Lists.newArrayList(
				PageName.STAR_TREK_GAME_PERFORMERS,
				PageName.MAJEL_BARRETT,
				PageName.MAJEL_BARRETT_RODDENBERRY,
				PageName.DAVID_KEITH_ANDERSON,
				PageName.DAVID_ANDERSON
		));
	}

}
