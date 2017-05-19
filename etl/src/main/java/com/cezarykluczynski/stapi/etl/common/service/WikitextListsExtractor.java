package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
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

	private static final String ASTERISK = "*";
	private static final String HASH = "#";
	private static final String SEMICOLON = ";";
	private static final String COLON = ":";

	private static final Pattern REMOVE_COMMENTS = Pattern.compile("<!--(.+?)-->", Pattern.DOTALL);

	public List<WikitextList> extractListsFromWikitext(String wikitext) {
		List<WikitextList> wikitextListList = Lists.newArrayList();
		wikitextListList.addAll(extractListLikeFromWikitext(wikitext, ASTERISK, ASTERISK));
		wikitextListList.addAll(extractListLikeFromWikitext(wikitext, HASH, HASH));
		return wikitextListList;
	}


	public List<WikitextList> extractDefinitionsFromWikitext(String wikitext) {
		return extractListLikeFromWikitext(wikitext, SEMICOLON, COLON);
	}

	public List<WikitextList> flattenWikitextListList(List<WikitextList> wikitextListList) {
		List<WikitextList> flattenWikitextListList = Lists.newArrayList();

		wikitextListList.forEach(wikitextList -> {
			flattenWikitextListList.add(wikitextList);
			flattenWikitextListList.addAll(flattenWikitextListList(wikitextList.getChildren()));
		});

		return flattenWikitextListList;
	}

	private List<WikitextList> extractListLikeFromWikitext(String wikitext, String firstLevelMarker, String nextLevelMarker) {
		List<WikitextList> wikitextListList = Lists.newArrayList();

		if (StringUtils.isEmpty(wikitext)) {
			return wikitextListList;
		}

		String wikitextWithoutComments = clearComments(wikitext);
		List<String> lines = trimLines(wikitextWithoutComments);

		for (String line : lines) {
			if (StringUtil.startsWithAnyIgnoreCase(line, Lists.newArrayList(firstLevelMarker, nextLevelMarker))) {
				addLineToWikitextListList(line, wikitextListList, firstLevelMarker, nextLevelMarker);
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

	private void addLineToWikitextListList(String line, List<WikitextList> wikitextListList, String firstLevelMarker, String nextLevelMarker) {
		WikitextList wikitextList = createWikitextList(line, firstLevelMarker, nextLevelMarker);
		int level = wikitextList.getLevel();

		List<WikitextList> lastWikitextListListAtLevel = getLastWikitextListListAtLevel(wikitextListList, level);
		if (level < 2 || !wikitextListList.isEmpty()) {
			lastWikitextListListAtLevel.add(wikitextList);
		}
	}

	private WikitextList createWikitextList(String line, String firstLevelMarker, String nextLevelMarker) {
		int level = determineLevel(line, firstLevelMarker, nextLevelMarker);

		WikitextList wikitextList = new WikitextList();
		wikitextList.setLevel(level);
		wikitextList.setText(StringUtils.trim(StringUtils.substring(line, level)));

		return wikitextList;
	}

	private int determineLevel(String line, String firstLevelMarker, String nextLevelMarker) {
		char[] characters = line.toCharArray();
		boolean markersDiffers = !firstLevelMarker.equals(nextLevelMarker);
		boolean lookingForNextLevelMarkers = false;
		for (int i = 0; i < line.length(); i++) {
			boolean atFirstChar = i == 0;
			char firstLevelMarkerChar = firstLevelMarker.charAt(0);
			char nextLevelMarkerChar = nextLevelMarker.charAt(0);

			if (atFirstChar) {
				lookingForNextLevelMarkers = characters[0] == nextLevelMarkerChar;
			} else if (lookingForNextLevelMarkers && characters[i] != nextLevelMarkerChar && markersDiffers) {
				return i + 1;
			} else if (characters[i] != firstLevelMarkerChar) {
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
