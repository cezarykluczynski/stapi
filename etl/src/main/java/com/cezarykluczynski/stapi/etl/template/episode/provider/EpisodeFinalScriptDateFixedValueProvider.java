package com.cezarykluczynski.stapi.etl.template.episode.provider;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class EpisodeFinalScriptDateFixedValueProvider implements FixedValueProvider<String, LocalDate> {

	private static final Map<String, LocalDate> FIXED_DATES = Maps.newHashMap();

	static {
		FIXED_DATES.put("Elaan of Troyius", LocalDate.of(1968, 5, 27));
		FIXED_DATES.put("Beyond the Farthest Star", LocalDate.of(1973, 5, 10));
		FIXED_DATES.put("The Lorelei Signal", LocalDate.of(1973, 6, 5));
		FIXED_DATES.put("Ex Post Facto", LocalDate.of(1994, 12, 15));
		FIXED_DATES.put("Proving Ground", LocalDate.of(2003, 10, 28));
		FIXED_DATES.put("Dagger of the Mind", LocalDate.of(1966, 8, 8));
		FIXED_DATES.put("The Menagerie, Part II", LocalDate.of(1966, 10, 7));
		FIXED_DATES.put("That Which Survives", LocalDate.of(1968, 9, 16));
		FIXED_DATES.put("What Are Little Girls Made Of?", LocalDate.of(1966, 7, 27));
		FIXED_DATES.put("Albatross", LocalDate.of(1974, 6, 27));
		FIXED_DATES.put("Bem", null);
		FIXED_DATES.put("The Practical Joker", LocalDate.of(1974, 5, 6));
		FIXED_DATES.put("True Q", LocalDate.of(1992, 8, 26));
		FIXED_DATES.put("In the Cards", LocalDate.of(1997, 3, 21));
		FIXED_DATES.put("Lifesigns", LocalDate.of(1995, 12, 4));
	}

	@Override
	public FixedValueHolder<LocalDate> getSearchedValue(String key) {
		return FixedValueHolder.of(FIXED_DATES.containsKey(key), FIXED_DATES.get(key));
	}

}
