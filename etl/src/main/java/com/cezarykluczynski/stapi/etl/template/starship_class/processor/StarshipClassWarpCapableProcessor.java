package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarshipClassWarpCapableProcessor implements ItemProcessor<String, Boolean> {

	private static final String[] WARP_PAGE_TITLES = {"Warp-capable", "Warp factor", "Transwarp drive", "Transwarp"};

	private final WikitextApi wikitextApi;

	public StarshipClassWarpCapableProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public Boolean process(String item) throws Exception {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(item);
		return pageTitleList.stream()
				.anyMatch(pageTitle -> StringUtils.equalsAnyIgnoreCase(pageTitle, WARP_PAGE_TITLES));
	}

}
