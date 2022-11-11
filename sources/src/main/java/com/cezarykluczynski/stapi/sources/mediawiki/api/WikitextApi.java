package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;

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
