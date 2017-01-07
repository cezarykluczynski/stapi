package com.cezarykluczynski.stapi.sources.mediawiki.cache;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FrequentHitCachingHelperDumpFormatter {

	private static final List<MediaWikiSource> sourcesOrder = Lists.newArrayList(MediaWikiSource.MEMORY_ALPHA_EN,
			MediaWikiSource.MEMORY_BETA_EN);

	public String format(Map<MediaWikiSource, Map<String, Integer>> cacheMap) {
		String output = "\n";
		Map<MediaWikiSource, List<Pair<String, Integer>>> sortedTitlesMap = Maps.newHashMap();

		for (MediaWikiSource mediaWikiSource : sourcesOrder) {
			List<Pair<String, Integer>> cachedTitlesPairList = cacheMap.get(mediaWikiSource).entrySet()
					.stream()
					.sorted((left, right) -> right.getValue().compareTo(left.getValue()))
					.map(stringIntegerEntry -> Pair.of(stringIntegerEntry.getKey(), stringIntegerEntry.getValue()))
					.collect(Collectors.toList());

			sortedTitlesMap.put(mediaWikiSource, cachedTitlesPairList);
		}

		final int max = sortedTitlesMap.entrySet().stream().map(entry -> {
			List<Integer> integerList = entry.getValue().stream().map(Pair::getValue).collect(Collectors.toList());
			return integerList.isEmpty() ? 0 : Collections.max(integerList);
		}).max(Integer::compare).orElse(0);

		int numberOfDigits = String.valueOf(max).length();

		for (MediaWikiSource mediaWikiSource : sourcesOrder) {
			output += mediaWikiSource.name() + ":\n";

			List<Pair<String, Integer>> pairList = sortedTitlesMap.get(mediaWikiSource);

			for (Pair<String, Integer> pair : pairList) {
				output += StringUtils.leftPad(String.valueOf(pair.getValue()), numberOfDigits + 1) +
						" :: " + pair.getKey() + "\n";
			}
		}

		return output.substring(0, output.length() - 1);
	}

}
