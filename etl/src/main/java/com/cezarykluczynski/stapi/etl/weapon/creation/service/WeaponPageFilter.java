package com.cezarykluczynski.stapi.etl.weapon.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponPageFilter implements MediaWikiPageFilter {

	private static final List<String> NOT_WEAPONS = Lists.newArrayList("Powder horn", "Kill setting");

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || NOT_WEAPONS.contains(page.getTitle());
	}

}
