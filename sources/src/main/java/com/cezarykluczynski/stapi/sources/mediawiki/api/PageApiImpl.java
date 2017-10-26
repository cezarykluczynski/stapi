package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLParseParser;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLQueryParser;
import com.cezarykluczynski.stapi.sources.mediawiki.service.complement.ParseComplementingService;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import info.bliki.api.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageApiImpl implements PageApi {

	private static final String REDIRECT_PREFIX = "#redirect";

	private static final Pattern SECTION_HEADER = Pattern.compile("(={1,6})(?!=.+?)(={1,6})");

	private final BlikiConnector blikiConnector;

	private final WikitextApi wikitextApi;

	private final ParseComplementingService parseComplementingService;

	private final PageCacheStorage pageCacheStorage;

	public PageApiImpl(BlikiConnector blikiConnector, WikitextApi wikitextApi, ParseComplementingService parseComplementingService,
			PageCacheStorage pageCacheStorage) {
		this.blikiConnector = blikiConnector;
		this.wikitextApi = wikitextApi;
		this.parseComplementingService = parseComplementingService;
		this.pageCacheStorage = pageCacheStorage;
	}

	@Override
	public Page getPage(String title, MediaWikiSource mediaWikiSource) {
		return getPageRedirectAware(title, 0, mediaWikiSource);
	}

	@Override
	public List<Page> getPages(List<String> titleList, MediaWikiSource mediaWikiSource) {
		List<Page> pageList = Lists.newArrayList();

		for (String title : titleList) {
			Page page = getPage(title, mediaWikiSource);

			if (page == null) {
				log.debug("Could not get page with title {}", title);
				continue;
			}

			pageList.add(page);
		}

		return pageList;
	}

	@Override
	public PageInfo getPageInfo(String title, MediaWikiSource mediaWikiSource) {
		String pageBody = blikiConnector.getPageInfo(title, mediaWikiSource);
		return parsePageInfo(pageBody);
	}

	private Page getPageRedirectAware(String title, int redirectCount, MediaWikiSource mediaWikiSource) {
		if (redirectCount == 0) {
			Page cachedPage = pageCacheStorage.get(title, mediaWikiSource);
			if (cachedPage != null) {
				return cachedPage;
			}
		}

		String pageBody = blikiConnector.getPage(title, mediaWikiSource);

		Page page = fromPageBody(pageBody, title, mediaWikiSource);
		supplementSectionsWikitext(page);
		supplementMediaWikiSource(page, mediaWikiSource);

		if (redirectCount == 2) {
			return page;
		}

		if (page == null) {
			log.info("Page with title {} and source {} was not found after {} redirects", title, mediaWikiSource, redirectCount);
			return null;
		}

		return pageOrRedirect(page, title, redirectCount, mediaWikiSource);
	}

	private Page fromPageBody(String pageBody, String title, MediaWikiSource mediaWikiSource) {
		Page page;

		if (pageBody == null) {
			log.warn("Null returned instead of page {} body", title);
			page = null;
		} else {
			page = parseInfo(pageBody, mediaWikiSource);
		}

		return page;
	}

	private Page redirectFromWikitextOrPage(String wikitext, Page page, String title, int redirectCount, MediaWikiSource mediaWikiSource) {
		List<String> redirects = wikitextApi.getPageTitlesFromWikitext(wikitext);
		if (redirects.isEmpty()) {
			log.warn("Page {} appears to be redirect, but no page to redirect to was found", title);
			return page;
		} else {
			String redirectTarget = redirects.get(0);
			log.debug("Following redirect from {} to {}", title, redirectTarget);
			Page redirectPage = getPageRedirectAware(redirectTarget, redirectCount + 1, mediaWikiSource);

			if (redirectPage == null) {
				log.warn("Redirect {} leaded to unexisting page", redirectTarget);
				return null;
			}

			PageHeader pageHeader = new PageHeader();
			pageHeader.setTitle(title);
			pageHeader.setPageId(page.getPageId());
			pageHeader.setMediaWikiSource(page.getMediaWikiSource());

			redirectPage.getRedirectPath().add(pageHeader);
			return redirectPage;
		}
	}

	private void supplementSectionsWikitext(Page page) {
		if (page == null) {
			return;
		}

		List<PageSection> sortedPageSectionList = page.getSections()
				.stream()
				.sorted((left, right) -> left.getByteOffset().compareTo(right.getByteOffset()))
				.collect(Collectors.toList());
		String wikitext = page.getWikitext();

		for (int i = 0; i < sortedPageSectionList.size(); i++) {
			PageSection pageSection = sortedPageSectionList.get(i);
			Integer nextIndex;

			try {
				nextIndex = sortedPageSectionList.get(i + 1).getByteOffset();
			} catch (IndexOutOfBoundsException e) {
				nextIndex = wikitext.length();
			}

			String sectionWikitext = wikitext.substring(pageSection.getByteOffset(), nextIndex);

			Matcher matcher = SECTION_HEADER.matcher(sectionWikitext);

			if (matcher.find()) {
				sectionWikitext = sectionWikitext.substring(matcher.end(), sectionWikitext.length());
			}

			sortedPageSectionList.get(i).setWikitext(StringUtils.trim(sectionWikitext));
		}
	}

	private void supplementMediaWikiSource(Page page, MediaWikiSource mediaWikiSource) {
		if (page != null) {
			page.setMediaWikiSource(mediaWikiSource);
		}
	}

	private Page pageOrRedirect(Page page, String title, int redirectCount, MediaWikiSource mediaWikiSource) {
		String wikitext = page.getWikitext();

		if (wikitext == null) {
			return page;
		}

		if (StringUtils.containsIgnoreCase(wikitext.substring(0, Math.min(200, wikitext.length())), REDIRECT_PREFIX)) {
			return redirectFromWikitextOrPage(wikitext, page, title, redirectCount, mediaWikiSource);
		}

		return page;
	}

	private PageInfo parsePageInfo(String xml) {
		try {
			XMLQueryParser xmlQueryParser = new XMLQueryParser(xml);
			return xmlQueryParser.getPageInfo();
		} catch (Exception e) {
			throw new StapiRuntimeException(e);
		}
	}

	private Page parseInfo(String xml, MediaWikiSource mediaWikiSource) {
		try {
			XMLParseParser xmlParseParser = new XMLParseParser(xml);
			Page page = xmlParseParser.getPage();

			if (page != null) {
				page.setMediaWikiSource(mediaWikiSource);
				parseComplementingService.complement(page);
			}
			return page;
		} catch (Exception e) {
			throw new StapiRuntimeException(e);
		}
	}

}
