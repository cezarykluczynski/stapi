package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WikitextApiImpl implements WikitextApi {

	private static final Pattern LINK = Pattern.compile("\\[\\[(.+?)]]");

	@Override
	public List<String> getPageTitlesFromWikitext(String wikitext) {
		Matcher matcher = LINK.matcher(wikitext);
		List<String> allMatches = Lists.newArrayList();

		while (matcher.find()) {
			String group = matcher.group(1);
			allMatches.add(StringUtils.trim(StringUtils.substringBefore(group, "|")));
		}

		return allMatches;
	}

}
