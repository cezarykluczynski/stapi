package com.cezarykluczynski.stapi.etl.title.creation.service;

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class RanksTemplateService {

	private static final String FLEET = "Fleet:";
	private static final String MILITARY = "Military:";
	private static final String SERVICES = "Services:";

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final Set<String> fleetRanks = Sets.newHashSet();
	private final Set<String> militaryRanks = Sets.newHashSet();

	private boolean initialized;

	public RanksTemplateService(PageApi pageApi, WikitextApi wikitextApi) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
	}

	public synchronized void initialize() {
		if (initialized) {
			return;
		}
		initialized = true;
		Page page = pageApi.getTemplate(TemplateTitle.RANKS, MediaWikiSource.MEMORY_ALPHA_EN);

		if (page == null) {
			return;
		}

		String fleetRanksText = StringUtils.substringAfter(page.getWikitext(), FLEET);
		String militaryRanksText = StringUtils.substringAfter(fleetRanksText, MILITARY);
		fleetRanksText = StringUtils.substringBefore(fleetRanksText, MILITARY);
		militaryRanksText = StringUtils.substringBefore(militaryRanksText, SERVICES);

		fleetRanks.addAll(wikitextApi.getPageTitlesFromWikitext(fleetRanksText));
		militaryRanks.addAll(wikitextApi.getPageTitlesFromWikitext(militaryRanksText));
	}

	public boolean isFleetRank(String titleName) {
		initialize();
		return StringUtil.containsIgnoreCase(fleetRanks, titleName);
	}

	public boolean isMilitaryRank(String titleName) {
		initialize();
		return StringUtil.containsIgnoreCase(militaryRanks, titleName);
	}

}
