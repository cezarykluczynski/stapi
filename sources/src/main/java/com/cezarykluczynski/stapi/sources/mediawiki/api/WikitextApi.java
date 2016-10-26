package com.cezarykluczynski.stapi.sources.mediawiki.api;

import java.util.List;

public interface WikitextApi {

	List<String> getPageTitlesFromWikitext(String wikitext);

}
