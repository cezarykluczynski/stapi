package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;

import java.util.List;

public interface WikitextApi {

	List<String> getPageTitlesFromWikitext(String wikitext);

	List<PageLink> getPageLinksFromWikitext(String wikitext);

}
