package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WikitextApiImpl implements WikitextApi {

	private static final Integer LINK_CONTENTS_GROUP = 1;

	private static final Integer WIKITEXT_LINK_PADDING = 2;

	private static final String PIPE = "|";

	private static final Pattern LINK = Pattern.compile("\\[\\[(.+?)]]");

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

		Matcher matcher = LINK.matcher(wikitext);
		List<PageLink> allMatches = Lists.newArrayList();

		while (matcher.find()) {
			String group = matcher.group(LINK_CONTENTS_GROUP);
			PageLink pageLink = new PageLink();
			pageLink.setTitle(StringUtils.trim(StringUtils.substringBefore(group, PIPE)));
			pageLink.setDescription(group.contains(PIPE) ? StringUtils.trim(StringUtils.substringAfter(group, PIPE)) : null);
			pageLink.setStartPosition(matcher.start(LINK_CONTENTS_GROUP) - WIKITEXT_LINK_PADDING);
			pageLink.setEndPosition(matcher.end(LINK_CONTENTS_GROUP) + WIKITEXT_LINK_PADDING);
			allMatches.add(pageLink);
		}

		return allMatches;
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
		if (!TemplateName.DIS.equals(template.getTitle())) {
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
				log.warn("Two template parts were found, but \"{}\" key was not found", TWO);
				return templatePart1.getValue();
			} else if (templatePart1 != null) {
				return templatePart1.getValue() + " (" + templatePart2.getValue() + ")";
			}
		}

		return null;
	}

	private Template.Part getTemplatePartByKey(List<Template.Part> templatePartList, String key) {
		return templatePartList
				.stream()
				.filter(part -> key.equals(part.getKey()))
				.findFirst().orElse(null);
	}

}
