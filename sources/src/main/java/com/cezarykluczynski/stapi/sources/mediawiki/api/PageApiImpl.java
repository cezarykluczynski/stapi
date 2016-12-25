package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.XMLParseParser;
import com.google.common.collect.Lists;
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

		Page page;
		if (pageBody == null) {
			log.warn("Null returned instead of page {} body", title);
			page = null;
		} else {
			page = parsePageInfo(pageBody);
		}

		if (page != null) {
			supplementSectionsWikitext(page);
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
				String redirectTarget = redirects.get(0);
				log.info("Following redirect from {} to {}", title, redirectTarget);
				Page redirectPage = getPage(redirectTarget, redirectCount + 1, mediaWikiSource);

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

		return page;
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
