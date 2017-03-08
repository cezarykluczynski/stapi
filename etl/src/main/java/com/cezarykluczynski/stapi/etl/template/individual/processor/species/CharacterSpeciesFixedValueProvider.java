package com.cezarykluczynski.stapi.etl.template.individual.processor.species;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.Fraction;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CharacterSpeciesFixedValueProvider implements FixedValueProvider<String, Map<String, Fraction>> {

	private static final String HUMAN = "Human";
	private static final String Q = "Q (species)";
	private static final String PRALOR = "Pralor";

	private static final Map<String, Map<String, Fraction>> MAP = Maps.newHashMap();

	static {
		MAP.put("Onaya", Maps.newHashMap());
		MAP.put("Nakamura", ImmutableMap.of(HUMAN, Fraction.getFraction(1, 1)));
		MAP.put("Lal", Maps.newHashMap());
		MAP.put("Porthos", Maps.newHashMap());
		MAP.put("Grathon Tolar", Maps.newHashMap());
		MAP.put("Arne Darvin", ImmutableMap.of("Klingon", Fraction.getFraction(1, 1)));
		MAP.put("Lora", Maps.newHashMap());
		MAP.put("Arturis", ImmutableMap.of("Species 116", Fraction.getFraction(1, 1)));
		MAP.put("Companion", ImmutableMap.of(HUMAN, Fraction.getFraction(1, 1)));
		MAP.put("Nancy Crater", Maps.newHashMap());
		MAP.put("Amanda Rogers", ImmutableMap.of(Q, Fraction.getFraction(1, 1)));
		MAP.put("Thelev", ImmutableMap.of("Orion", Fraction.getFraction(1, 1)));
		MAP.put("Tuvix", ImmutableMap.of(
				"Vulcan", Fraction.getFraction(1, 3),
				"Talaxian", Fraction.getFraction(7, 24),
				"Mylean", Fraction.getFraction(1, 24)));
		MAP.put("Prototype Unit 0001", ImmutableMap.of(PRALOR, Fraction.getFraction(1, 1)));
		MAP.put("Quinn", ImmutableMap.of(Q, Fraction.getFraction(1, 1)));
		MAP.put("Juliana Tainer", Maps.newHashMap());
		MAP.put("Brota", Maps.newHashMap());
		MAP.put("Automated Unit 3947", ImmutableMap.of(PRALOR, Fraction.getFraction(1, 1)));
		MAP.put("Odo", ImmutableMap.of("Changeling", Fraction.getFraction(1, 1)));
		MAP.put("Miles O'Brien", ImmutableMap.of(HUMAN, Fraction.getFraction(1, 1)));
		MAP.put("Automated Unit 6263", ImmutableMap.of(PRALOR, Fraction.getFraction(1, 1)));
		MAP.put("Automated Commander 122", ImmutableMap.of("Cravic", Fraction.getFraction(1, 1)));
		MAP.put("B-4", Maps.newHashMap());
		MAP.put("Sim", ImmutableMap.of(HUMAN, Fraction.getFraction(1, 1)));
		MAP.put("Q", ImmutableMap.of(Q, Fraction.getFraction(1, 1)));
		MAP.put("Curzon Dax", ImmutableMap.of("Trill", Fraction.getFraction(1, 1)));
	}

	@Override
	public FixedValueHolder<Map<String, Fraction>> getSearchedValue(String key) {
		return FixedValueHolder.of(MAP.containsKey(key), MAP.get(key));
	}

}
