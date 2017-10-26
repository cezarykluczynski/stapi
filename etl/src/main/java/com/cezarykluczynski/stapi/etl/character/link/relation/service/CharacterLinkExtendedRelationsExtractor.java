package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CharacterLinkExtendedRelationsExtractor {

	private static final Pattern INSIDE_PARENTHESES = Pattern.compile("\\(([^)]+)\\)");

	private final WikitextApi wikitextApi;

	public CharacterLinkExtendedRelationsExtractor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	public List<CharacterPageLinkWithRelationName> extract(String wikitext) {
		List<CharacterPageLinkWithRelationName> characterPageLinkWithRelationNameList = Lists.newArrayList();
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(wikitext);

		for (int i = 0; i < pageLinkList.size(); i++) {
			PageLink pageLink = pageLinkList.get(i);
			PageLink nextPageLink = pageLinkList.size() - 1 == i ? null : pageLinkList.get(i + 1);
			int nextPageLinkStart = nextPageLink == null ? wikitext.length() : nextPageLink.getStartPosition();
			String parenthesesCandidate = StringUtils.substring(wikitext, pageLink.getStartPosition(), nextPageLinkStart);
			Matcher matcher = INSIDE_PARENTHESES.matcher(parenthesesCandidate);
			boolean hasParentheses = false;
			while (matcher.find()) {
				hasParentheses = true;
				characterPageLinkWithRelationNameList.add(CharacterPageLinkWithRelationName.of(pageLink, matcher.group(1)));
			}

			if (!hasParentheses) {
				characterPageLinkWithRelationNameList.add(CharacterPageLinkWithRelationName.of(pageLink, null));
			}
		}

		return characterPageLinkWithRelationNameList;
	}

}
