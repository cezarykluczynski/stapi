package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WikitextApiImpl implements WikitextApi {

	private static final String PIPE = "|";
	private static final Pattern LINK = Pattern.compile("\\[\\[(.+?)]]");

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
			String group = matcher.group(1);
			PageLink pageLink = new PageLink();
			pageLink.setTitle(StringUtils.trim(StringUtils.substringBefore(group, PIPE)));
			pageLink.setDescription(group.contains(PIPE) ? StringUtils.trim(StringUtils.substringAfter(group, PIPE))
					: null);
			allMatches.add(pageLink);
		}

		return allMatches;
	}

}
