package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WikitextApiImpl implements WikitextApi {

	private static final Integer LINK_CONTENTS_GROUP = 1;
	private static final Integer PADDING = 2;
	private static final String PIPE = "|";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";
	private static final String SPACE = " ";

	private static final Pattern LINK = Pattern.compile("\\[\\[(.+?)]]");
	private static final Pattern DIS_LINK = Pattern.compile("\\{\\{dis\\|(.+?)}}");
	private static final Pattern MU_LINK = Pattern.compile("\\{\\{mu\\|(.+?)}}");
	private static final Pattern ALT_LINK = Pattern.compile("\\{\\{alt\\|(.+?)}}");
	private static final Pattern FEDERATION_LINK = Pattern.compile("\\{\\{federation}}");

	private static final Pattern MULTILINE_WITHOUT_TEMPLATES = Pattern.compile("\\{\\{(.*?)}}", Pattern.DOTALL);
	private static final Pattern NO_INCLUDE_PATTERN = Pattern.compile("<noinclude>(.+?)</noinclude>", Pattern.DOTALL);

	private static final String ONE = "1";
	private static final String TWO = "2";

	@Override
	public List<String> getPageTitlesFromWikitext(String wikitext) {
		return getPageLinksFromWikitext(wikitext)
				.stream()
				.map(PageLink::getTitle)
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getPageTitlesFromWikitextExcludingNotFound(String wikitext, Page page) {
		final Document htmlDocument = page.getHtmlDocument();
		List<PageLink> nonExistingPageLinks = htmlDocument.select("span.new").stream()
				.map(element -> {
					PageLink pageLink = new PageLink();
					pageLink.setTitle(element.attr("title").replace(" (page does not exist)", ""));
					pageLink.setDescription(element.text());
					return pageLink;
				})
				.collect(Collectors.toList());

		final List<PageLink> pageLinksFromWikitext = getPageLinksFromWikitext(wikitext);

		return pageLinksFromWikitext
				.stream()
				.filter(pageLink -> {
					return nonExistingPageLinks.stream().noneMatch(nonExistingPageLink ->
							StringUtils.equals(nonExistingPageLink.getTitle(), pageLink.getTitle())
									&& (pageLink.getDescription() == null
									|| StringUtils.equals(nonExistingPageLink.getDescription(), pageLink.getDescription())));
				})
				.map(PageLink::getTitle)
				.collect(Collectors.toList());
	}

	@Override
	public List<PageLink> getPageLinksFromWikitext(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<PageLink> allMatches = Lists.newArrayList();

		allMatches.addAll(extractLinkMatches(wikitext));
		allMatches.addAll(extractMatches(wikitext, DIS_LINK, 2, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + templateParts.get(1) + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, MU_LINK, 1, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + "mirror" + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, ALT_LINK, 1, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + "alternate reality" + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, FEDERATION_LINK, 0, templateParts -> "United Federation of Planets"));

		return allMatches
				.stream()
				.sorted(Comparator.comparing(PageLink::getStartPosition))
				.collect(Collectors.toList());
	}

	@Override
	public String getWikitextWithoutTemplates(String wikitext) {
		if (wikitext == null) {
			return null;
		}

		return MULTILINE_WITHOUT_TEMPLATES.matcher(wikitext).replaceAll("");
	}

	@Override
	public String getWikitextWithoutNoInclude(String wikitext) {
		if (wikitext == null) {
			return null;
		}

		return StringUtils.trim(NO_INCLUDE_PATTERN.matcher(wikitext).replaceAll(""));
	}

	@Override
	public String getWikitextWithoutLinks(String wikitext) {
		if (wikitext == null) {
			return null;
		}
		String wikitextWithoutLinks = wikitext;
		List<PageLink> pageLinkList = Lists.reverse(getPageLinksFromWikitext(wikitext));

		for (PageLink pageLink : pageLinkList) {
			String pageLinkDescription = MoreObjects.firstNonNull(pageLink.getDescription(), pageLink.getTitle());
			wikitextWithoutLinks = wikitextWithoutLinks.substring(0,
					pageLink.getStartPosition()) + pageLinkDescription + wikitextWithoutLinks.substring(pageLink.getEndPosition());
		}

		return wikitextWithoutLinks;
	}

	@Override
	public String disTemplateToPageTitle(Template template) {
		if (!TemplateTitle.DIS.equals(template.getTitle())) {
			return null;
		}

		List<Template.Part> templatePartList = template.getParts();
		if (templatePartList.size() == 1) {
			Template.Part templatePart = getTemplatePartByKey(templatePartList, ONE);
			return templatePart == null ? null : templatePart.getValue();
		} else if (templatePartList.size() == 2) {
			Template.Part templatePart1 = getTemplatePartByKey(templatePartList, ONE);
			Template.Part templatePart2 = getTemplatePartByKey(templatePartList, TWO);
			if (templatePart1 != null && templatePart2 == null) {
				log.info("Two template parts were found, but \"{}\" key was not found", TWO);
				return templatePart1.getValue();
			} else if (templatePart1 != null) {
				return templatePart1.getValue() + SPACE + LEFT_BRACKET + templatePart2.getValue() + RIGHT_BRACKET;
			}
		}

		return null;
	}

	@Override
	public List<String> getTemplateNamesFromWikitext(String wikitext) {
		if (wikitext == null) {
			return null;
		}
		String wikitextNoWhiteChars = wikitext.replaceAll("[\\t|\\r\\n]+", PIPE);
		List<String> allMatches = Lists.newArrayList();

		final Matcher matcher = MULTILINE_WITHOUT_TEMPLATES.matcher(wikitextNoWhiteChars);
		while (matcher.find()) {
			allMatches.add(matcher.group());
		}

		allMatches = allMatches.stream()
				.map(s -> {
					String result = s.substring(2, s.length() - 2).trim();
					result = StringUtils.substringBefore(result, PIPE);
					return result;
				})
				.collect(Collectors.toList());

		return allMatches;
	}

	private List<PageLink> extractLinkMatches(String wikitext) {
		List<PageLink> linkMatches = Lists.newArrayList();
		Matcher linkMatcher = LINK.matcher(wikitext);

		while (linkMatcher.find()) {
			String group = linkMatcher.group(LINK_CONTENTS_GROUP);
			PageLink pageLink = new PageLink();
			pageLink.setTitle(StringUtils.trim(StringUtils.substringBefore(group, PIPE)));
			pageLink.setUntrimmedDescription(group.contains(PIPE) ? StringUtils.substringAfter(group, PIPE) : null);
			pageLink.setDescription(StringUtils.trim(pageLink.getUntrimmedDescription()));
			pageLink.setStartPosition(linkMatcher.start(LINK_CONTENTS_GROUP) - PADDING);
			pageLink.setEndPosition(linkMatcher.end(LINK_CONTENTS_GROUP) + PADDING);
			linkMatches.add(pageLink);
		}

		return linkMatches;
	}

	private List<PageLink> extractMatches(String wikitext, Pattern pattern, int minParts, Function<List<String>, String> linkConstructor) {
		List<PageLink> matches = Lists.newArrayList();
		Matcher matcher = pattern.matcher(wikitext);

		while (matcher.find()) {
			PageLink pageLink = new PageLink();
			List<String> templateParts = Lists.newArrayList();
			boolean hasExpectedGroup = matcher.groupCount() > 0 && minParts > 0;
			if (hasExpectedGroup) {
				String group = matcher.group(LINK_CONTENTS_GROUP);
				templateParts = Lists.newArrayList(group.split("\\" + PIPE));

				if (templateParts.size() < minParts) {
					continue;
				}
			}

			String title = linkConstructor.apply(templateParts);
			pageLink.setTitle(title);
			pageLink.setDescription(templateParts.isEmpty() ? title : templateParts.get(0));

			if (templateParts.size() == minParts + 1) {
				pageLink.setDescription(templateParts.get(minParts));
			}

			pageLink.setStartPosition(!hasExpectedGroup ? matcher.toMatchResult().start() : matcher.start(LINK_CONTENTS_GROUP) - PADDING);
			pageLink.setEndPosition(!hasExpectedGroup ? matcher.toMatchResult().end() : matcher.end(LINK_CONTENTS_GROUP) + PADDING);
			matches.add(pageLink);
		}

		return matches;
	}

	private Template.Part getTemplatePartByKey(List<Template.Part> templatePartList, String key) {
		return templatePartList
				.stream()
				.filter(part -> key.equals(part.getKey()))
				.findFirst().orElse(null);
	}

}
