package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLParseParser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class PageApiImpl implements PageApi {

	private static final String REDIRECT_PREFIX = "#redirect";

	private BlikiConnector blikiConnector;

	private WikitextApi wikitextApi;

	@Inject
	public PageApiImpl(BlikiConnector blikiConnector, WikitextApi wikitextApi) {
		this.blikiConnector = blikiConnector;
		this.wikitextApi = wikitextApi;
	}

	@Override
	public Page getPage(String title, MediaWikiSource mediaWikiSource) {
		return getPage(title, 0, mediaWikiSource);
	}

	private Page getPage(String title, int redirectCount, MediaWikiSource mediaWikiSource) {
		String pageBody = blikiConnector.getPage(title, mediaWikiSource);
		Page page = parsePageInfo(pageBody);

		if (page != null) {
			page.setMediaWikiSource(mediaWikiSource);
		}

		if (redirectCount == 2) {
			return page;
		}

		if (page == null) {
			return null;
		}

		String wikitext = page.getWikitext();

		if (wikitext == null) {
			return page;
		}

		if (wikitext.substring(0, Math.min(200, wikitext.length())).contains(REDIRECT_PREFIX)) {
			List<String> redirects = wikitextApi.getPageTitlesFromWikitext(wikitext);
			if (redirects.isEmpty()) {
				log.warn("Page {} appears to be redirect, but no page to redirect to was found", title);
				return page;
			} else {
				log.info("Following redirect from {} to {}", title, redirects.get(0));
				Page redirectPage = getPage(redirects.get(0), redirectCount + 1, mediaWikiSource);
				redirectPage.getRedirectPath().add(PageHeader.builder()
						.title(title)
						.pageId(page.getPageId())
						.mediaWikiSource(mediaWikiSource)
						.build());
				return redirectPage;
			}
		}

		return page;
	}

	@Override
	public List<Page> getPages(List<String> titleList, MediaWikiSource mediaWikiSource) {
		List<Page> pageList = Lists.newArrayList();

		for (String title : titleList) {
			Page page = getPage(title, mediaWikiSource);

			if (page == null) {
				log.warn("Could not get page with title {}", title);
				continue;
			}

			pageList.add(page);
		}

		return pageList;
	}

	private Page parsePageInfo(String xml) {
		try {
			XMLParseParser xmlParseParser = new XMLParseParser(xml);
			return xmlParseParser.getPage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
