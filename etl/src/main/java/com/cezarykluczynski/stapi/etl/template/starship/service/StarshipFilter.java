package com.cezarykluczynski.stapi.etl.template.starship.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class StarshipFilter implements MediaWikiPageFilter {

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		// TODO
		return false;
	}

}
