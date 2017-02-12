package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WikitextListsExtractor {

	private static final String ASTERISK_STRING = "*";
	private static final char ASTERISK_CHAR = '*';
	private static final Pattern REMOVE_COMMENTS = Pattern.compile("<!--(.+?)-->", Pattern.DOTALL);

	public List<WikitextList> extractFromWikitext(String wikitext) {
		List<WikitextList> wikitextListList = Lists.newArrayList();

		if (StringUtils.isEmpty(wikitext)) {
			return wikitextListList;
		}

		String wikitextWithoutComments = clearComments(wikitext);
		List<String> lines = trimLines(wikitextWithoutComments);

		for (String line : lines) {
			if (line.startsWith(ASTERISK_STRING)) {
				addLineToWikitextListList(line, wikitextListList);
			}
		}

		return wikitextListList;
	}

	private String clearComments(String wikitext) {
		return REMOVE_COMMENTS.matcher(wikitext).replaceAll("");
	}

	private List<String> trimLines(String wikitextWithoutComments) {
		return Lists.newArrayList(wikitextWithoutComments.split("\n"))
				.stream()
				.map(StringUtils::trim)
				.collect(Collectors.toList());
	}

	private void addLineToWikitextListList(String line, List<WikitextList> wikitextListList) {
		WikitextList wikitextList = createWikitextList(line);
		int level = wikitextList.getLevel();

		List<WikitextList> lastWikitextListListAtLevel = getLastWikitextListListAtLevel(wikitextListList, level);
		lastWikitextListListAtLevel.add(wikitextList);
	}

	private WikitextList createWikitextList(String line) {
		int level = determineLevel(line);

		WikitextList wikitextList = new WikitextList();
		wikitextList.setLevel(level);
		wikitextList.setText(StringUtils.trim(StringUtils.substring(line, level)));

		return wikitextList;
	}

	private int determineLevel(String line) {
		char[] characters = line.toCharArray();
		for (int i = 0; i < line.length(); i++) {
			if (characters[i] != ASTERISK_CHAR) {
				return i;
			}
		}

		return 0;
	}

	private List<WikitextList> getLastWikitextListListAtLevel(List<WikitextList> wikitextListList, int level) {
		if (wikitextListList.isEmpty()) {
			return wikitextListList;
		}

		WikitextList lastWikitextList = wikitextListList.get(wikitextListList.size() - 1);

		if (lastWikitextList.getLevel() == level) {
			return wikitextListList;
		}

		return getLastWikitextListListAtLevel(lastWikitextList.getChildren(), level);
	}

}
