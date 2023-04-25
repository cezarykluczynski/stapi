package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;

import java.util.List;

public interface WikitextApi {

	List<String> getPageTitlesFromWikitext(String wikitext);

	List<String> getPageTitlesFromWikitextExcludingNotFound(String wikitext, Page page);

	List<PageLink> getPageLinksFromWikitext(String wikitext);

	String getWikitextWithoutTemplates(String wikitext);

	String getWikitextWithoutNoInclude(String wikitext);

	String getWikitextWithoutLinks(String wikitext);

	String disTemplateToPageTitle(Template template);

	List<String> getTemplateNamesFromWikitext(String wikitext);

}
