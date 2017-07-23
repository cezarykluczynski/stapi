package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.common;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradingCardItemsProcessor implements ItemProcessor<String, Integer> {

	private static final Pattern SECTION_PATTERN = Pattern.compile("([0-9,]{1,8})\\s?(\\+\\s?\\d)?\\s?([^\\d]\\w+)?", Pattern.MULTILINE);
	private static final List<String> LABEL_PRIORITIZED = Lists.newArrayList("Retail", "Blockbuster", "Wal-Mart", "Walmart", "Hobby");

	@Override
	public Integer process(String item) throws Exception {
		if (item == null) {
			return null;
		}

		List<NumberWithType> numbers = createNumbersList(item);

		if (numbers.isEmpty()) {
			return null;
		}

		Multimap<String, Integer> labelMultimap = numbersToMultimap(numbers);
		if (labelMultimap == null) {
			return null;
		}

		return extractHighestValueFromFirstEntry(labelMultimap);
	}

	private List<NumberWithType> createNumbersList(String item) {
		return getNumbers(item).stream()
				.sorted(this::compareIntsNullSafe)
				.collect(Collectors.toList());
	}

	private Multimap<String, Integer> numbersToMultimap(List<NumberWithType> numbersWithTypes) {
		Multimap<String, Integer> labelMultimap = LinkedListMultimap.create();

		for (int priority = 0; priority < numbersWithTypes.size(); priority++) {
			labelMultimap.put(numbersWithTypes.get(priority).getType(), numbersWithTypes.get(priority).getNumber());
		}

		return labelMultimap;
	}

	private Integer extractHighestValueFromFirstEntry(Multimap<String, Integer> labelMultimap) {
		List<Integer> values = Lists.newArrayList(labelMultimap.asMap().values().iterator().next());
		values.sort(Comparator.comparingInt(value -> value));
		return values.get(values.size() - 1);
	}

	private int compareIntsNullSafe(NumberWithType left, NumberWithType right) {
		Integer leftPriority = getPriority(left.getType());
		Integer rightPriority = getPriority(right.getType());

		if (leftPriority == null) {
			return 1;
		}

		if (rightPriority == null) {
			return -1;
		}

		return Integer.compare(leftPriority, rightPriority);
	}

	private Integer getPriority(String type) {
		for (int priority = 0; priority < LABEL_PRIORITIZED.size(); priority++) {
			if (StringUtils.containsIgnoreCase(LABEL_PRIORITIZED.get(priority), type)) {
				return priority;
			}
		}
		return null;
	}

	private List<NumberWithType> getNumbers(String item) {
		Matcher sectionMatcher = SECTION_PATTERN.matcher(item);

		List<NumberWithType> numbersWithTypes = Lists.newArrayList();

		while (sectionMatcher.find()) {
			String numberCandidate = sectionMatcher.group(1).replaceAll(",|\\.", "");
			NumberWithType numberWithType = NumberWithType.of(Ints.tryParse(numberCandidate), sectionMatcher.group(3));
			numbersWithTypes.add(numberWithType);
		}

		return numbersWithTypes;
	}

	@Data
	@AllArgsConstructor(staticName = "of")
	private static class NumberWithType {

		private Integer number;

		private String type;

	}

}
