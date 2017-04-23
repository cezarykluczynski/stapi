package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLParseParser;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLQueryParser;
import com.cezarykluczynski.stapi.sources.mediawiki.service.complement.ParseComplementingService;
import com.google.common.collect.Lists;
import info.bliki.api.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageApiImpl implements PageApi {

	private static final String REDIRECT_PREFIX = "#redirect";
	private static final Pattern SECTION_HEADER = Pattern.compile("(={1,6})(?!=.+?)(={1,6})");

	private BlikiConnector blikiConnector;

	private WikitextApi wikitextApi;

	private ParseComplementingService parseComplementingService;

	@Inject
	public PageApiImpl(BlikiConnector blikiConnector, WikitextApi wikitextApi, ParseComplementingService parseComplementingService) {
		this.blikiConnector = blikiConnector;
		this.wikitextApi = wikitextApi;
		this.parseComplementingService = parseComplementingService;
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
				log.warn("Could not get page with title {}", title);
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
		String pageBody = blikiConnector.getPage(title, mediaWikiSource);

		Page page;
		if (pageBody == null) {
			log.warn("Null returned instead of page {} body", title);
			page = null;
		} else {
			page = parseInfo(pageBody, mediaWikiSource);
		}

		if (page != null) {
			supplementSectionsWikitext(page);
			page.setMediaWikiSource(mediaWikiSource);
		}

		if (redirectCount == 2) {
			return page;
		}

		if (page == null) {
			log.info("Page with title {} and source {} was not found after {} redirects", title, mediaWikiSource, redirectCount);
			return null;
		}

		String wikitext = page.getWikitext();

		if (wikitext == null) {
			return page;
		}

		if (wikitext.substring(0, Math.min(200, wikitext.length())).contains(REDIRECT_PREFIX)) {
			return redirectFromWikitextOrPage(wikitext, page, title, redirectCount, mediaWikiSource);
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

			redirectPage.getRedirectPath().add(PageHeader.builder()
					.title(title)
					.pageId(page.getPageId())
					.mediaWikiSource(mediaWikiSource)
					.build());
			return redirectPage;
		}
	}

	private void supplementSectionsWikitext(Page page) {
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

	private PageInfo parsePageInfo(String xml) {
		try {
			XMLQueryParser xmlQueryParser = new XMLQueryParser(xml);
			return xmlQueryParser.getPageInfo();
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			throw new RuntimeException(e);
		}
	}

}
