package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatusProcessor implements ItemProcessor<String, String> {

	private static final String PRESUM = "Presum";
	private static final String UNKNOWN = "Unknown";
	private static final String ED = "ed";

	@Override
	public String process(String item) throws Exception {
		if (StringUtils.isEmpty(item) || StringUtils.containsIgnoreCase(item, PRESUM) || StringUtils.startsWithIgnoreCase(item, UNKNOWN)) {
			return null;
		}

		String itemToProcess = Lists.newArrayList(item.split(PatternDictionary.BR)).get(0);
		List<String> words = Lists.newArrayList(StringUtils.splitByWholeSeparator(itemToProcess, StringUtils.SPACE));

		if (words.size() == 1) {
			return capitalizeAndCleanFirst(words);
		}

		List<String> statusCandidates = getAdjectiveCandidates(words);

		if (statusCandidates.size() == 1) {
			return capitalizeAndCleanFirst(statusCandidates);
		}

		List<String> wordsWithoutTags = getWordsWithoutTags(words);

		if (wordsWithoutTags.size() == 1) {
			return capitalizeAndCleanFirst(wordsWithoutTags);
		}

		log.info("Could not map statship status candidate \"{}\" to starship status", item);
		return null;
	}

	private List<String> getAdjectiveCandidates(List<String> words) {
		return words.stream()
				.filter(this::mightBeAdjective)
				.collect(Collectors.toList());
	}

	private List<String> getWordsWithoutTags(List<String> words) {
		List<String> wordsWithoutTags = Lists.newArrayList();

		for (String word : words) {
			if (StringUtils.containsAny(word, "[[", "(", "<")) {
				break;
			}

			wordsWithoutTags.add(word);
		}

		return wordsWithoutTags;
	}

	private String capitalizeAndCleanFirst(List<String> candidates) {
		return StringUtils.removeAll(StringUtils.capitalize(candidates.get(0)), "'");
	}

	private boolean mightBeAdjective(String s) {
		return StringUtils.endsWith(s, ED);
	}

}
