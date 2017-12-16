package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO;
import com.cezarykluczynski.stapi.util.constant.Fraction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CardSizeProcessor implements ItemProcessor<String, CardSizeDTO> {

	private static final String[] XES = {"x", "×"};
	private static final String[] INCHES = {"”", "″", "“"};
	private static final String[] SLASHES = {"/", "⁄"};
	private static final String SPACE = " ";
	private static final String OPEN_BRACKET = "(";
	private static final String CLOSE_BRACKET = ")";
	private static final String CARD = "card";
	private static final Set<String> FRACTION_NAMES = Fraction.MAPPING.keySet();

	@Override
	public CardSizeDTO process(String item) throws Exception {
		List<String> groups = Lists.newArrayList(StringUtils.split(item, CLOSE_BRACKET));
		List<CardSizeDTO> cardSizeDTOList = Lists.newArrayList();
		Map<Integer, Boolean> hasCardsMap = Maps.newHashMap();
		int addingIndex = 0;

		for (int i = 0; i < groups.size(); i++) {
			String group = groups.get(i);
			if (StringUtils.containsAny(group, XES)) {
				List<String> parts = Lists.newArrayList(StringUtils.split(group, XES[0] + "|" + XES[1]));
				List<Double> results = Lists.newArrayList();
				for (String part : parts) {
					results.add(tryParseToDouble(part));
				}

				if (results.get(0) != null && results.get(1) != null) {
					if (groupHasCard(group)) {
						hasCardsMap.put(addingIndex, true);
					}
					addingIndex++;
					cardSizeDTOList.add(CardSizeDTO.of(results.get(0), results.get(1)));
				}
			}
		}

		if (hasMoreThanOneEntry(hasCardsMap) && hasMoreThanOneSize(cardSizeDTOList, hasCardsMap)) {
			return null;
		}

		if (cardSizeDTOList.size() >= 1) {
			return cardSizeDTOList.get(0);
		}

		return null;
	}

	private boolean groupHasCard(String group) {
		return StringUtils.contains(group, OPEN_BRACKET) && StringUtils.containsIgnoreCase(group, CARD);
	}

	private boolean hasMoreThanOneSize(List<CardSizeDTO> cardSizeDTOList, Map<Integer, Boolean> hasCardsMap) {
		Set<CardSizeDTO> cardSizeDTOSet = Sets.newHashSet();
		for (int i = 0; i < cardSizeDTOList.size(); i++) {
			if (hasCardsMap.get(i)) {
				cardSizeDTOSet.add(cardSizeDTOList.get(i));
			}
		}
		return cardSizeDTOSet.size() > 1;
	}

	private boolean hasMoreThanOneEntry(Map<Integer, Boolean> hasCardsMap) {
		return hasCardsMap.entrySet().stream()
				.map(Map.Entry::getValue)
				.filter(Boolean.TRUE::equals)
				.count() > 1;
	}

	private Double tryParseToDouble(String subject) {
		String trimmedPart = StringUtils.trim(subject);

		boolean containsInches = StringUtils.containsAny(trimmedPart, INCHES);
		if (hasInchesOrFractions(trimmedPart, containsInches)) {
			Double wholeNumber = Doubles.tryParse(charAt(trimmedPart, 0));
			Double fraction = 0d;
			if (StringUtils.containsAny(trimmedPart, SLASHES)) {
				if (!StringUtils.contains(trimmedPart, SPACE)) {
					wholeNumber = null;
				}
				List<String> parts = Lists.newArrayList(StringUtils.split(trimmedPart, " "));
				for (String part : parts) {
					if (StringUtils.containsAny(part, SLASHES)) {
						fraction = tryGetFractionWithSlashes(part, fraction);
						break;
					}
				}
			} else if (canTryParseFraction(trimmedPart, containsInches)) {
				fraction = tryGetFraction(trimmedPart, fraction);
			}

			if (wholeNumber != null) {
				return wholeNumber + fraction;
			}

			if (isNonZeroFraction(fraction)) {
				return fraction;
			}
		}

		return null;
	}

	private String charAt(String trimmedPart, int position) {
		return String.valueOf(trimmedPart.charAt(position));
	}

	private boolean hasInchesOrFractions(String trimmedPart, boolean containsInches) {
		return containsInches && trimmedPart.length() >= 2 || StringUtils.containsAny(trimmedPart, FRACTION_NAMES.toString());
	}

	private boolean canTryParseFraction(String trimmedPart, boolean containsInches) {
		return trimmedPart.length() >= 3 || (trimmedPart.length() >= 2 && !containsInches);
	}

	private boolean isNonZeroFraction(Double fraction) {
		return fraction != null && Double.compare(fraction, 0d) != 0;
	}

	private Double tryGetFraction(String trimmedPart, Double fraction) {
		String fractionCandidate = charAt(trimmedPart, 1);
		if (Fraction.MAPPING.containsKey(fractionCandidate)) {
			return Fraction.MAPPING.get(fractionCandidate);
		}
		return fraction;
	}

	private Double tryGetFractionWithSlashes(String part, Double fraction) {
		List<String> numberCandidates = Lists.newArrayList(StringUtils.split(part, joinArray(SLASHES)));
		Double numerator = Doubles.tryParse(numberCandidates.get(0));
		Double denominator = Doubles.tryParse(StringUtils.split(numberCandidates.get(1), joinArray(INCHES))[0]);
		if (numerator != null && denominator != null) {
			fraction = numerator / denominator;
		}
		return fraction;
	}

	private String joinArray(String[] arrayOfStrings) {
		return String.join(StringUtils.EMPTY, arrayOfStrings);
	}

}
