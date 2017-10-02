package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

	private static final Pattern MULTILINE_WITHOUT_TEMPLATES = Pattern.compile("\\{\\{(.*?)}}", Pattern.DOTALL);

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
	public List<PageLink> getPageLinksFromWikitext(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<PageLink> allMatches = Lists.newArrayList();

		allMatches.addAll(extractLinkMatches(wikitext));
		allMatches.addAll(extractDisMatches(wikitext));

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
	public String getWikitextWithoutLinks(String wikitext) {
		String wikitextWithoutLinks = wikitext;
		List<PageLink> pageLinkList = Lists.reverse(getPageLinksFromWikitext(wikitext));

		for (PageLink pageLink : pageLinkList) {
			String pageLinkDescription = Objects.firstNonNull(pageLink.getDescription(), pageLink.getTitle());
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

	private List<PageLink> extractDisMatches(String wikitext) {
		List<PageLink> disMatches = Lists.newArrayList();
		Matcher disMatcher = DIS_LINK.matcher(wikitext);

		while (disMatcher.find()) {
			String group = disMatcher.group(LINK_CONTENTS_GROUP);
			List<String> templateParts = Lists.newArrayList(group.split("\\" + PIPE));
			PageLink pageLink = new PageLink();

			if (templateParts.size() < 2) {
				continue;
			}

			String title = templateParts.get(0) + SPACE + LEFT_BRACKET + templateParts.get(1) + RIGHT_BRACKET;
			pageLink.setTitle(title);
			pageLink.setDescription(templateParts.get(0));

			if (templateParts.size() == 3) {
				pageLink.setDescription(templateParts.get(2));
			}

			pageLink.setStartPosition(disMatcher.start(LINK_CONTENTS_GROUP) - PADDING);
			pageLink.setEndPosition(disMatcher.end(LINK_CONTENTS_GROUP) + PADDING);
			disMatches.add(pageLink);
		}

		return disMatches;
	}

	private Template.Part getTemplatePartByKey(List<Template.Part> templatePartList, String key) {
		return templatePartList
				.stream()
				.filter(part -> key.equals(part.getKey()))
				.findFirst().orElse(null);
	}

}
