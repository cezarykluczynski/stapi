package com.cezarykluczynski.stapi.wiki.api;

import java.util.List;

public interface WikitextApi {

	List<String> pageTitlesFromWikitext(String wikitext);

}
