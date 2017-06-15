package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MagazineSeriesNumberOfIssuesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_NUMBER_OF_ISSUES = Maps.newHashMap();

	static {
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Magazine", 240);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Fact Files", 855);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Official Fan Club of the UK Magazine", 13);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Giant Poster Book", 18);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Next Generation Magazine (Marvel)", 25);
		TITLES_TO_NUMBER_OF_ISSUES.put("Inside Star Trek", 30);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Magazine", 52);
		TITLES_TO_NUMBER_OF_ISSUES.put("Cinefantastique", 198);
		TITLES_TO_NUMBER_OF_ISSUES.put("Fantastic Films", 46);
		TITLES_TO_NUMBER_OF_ISSUES.put("T-Negative", 35);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Next Generation - The Official Poster Magazine", 96);
		TITLES_TO_NUMBER_OF_ISSUES.put("Stardate", 16);
		TITLES_TO_NUMBER_OF_ISSUES.put("The Official Star Trek The Next Generation: Build the USS Enterprise NCC-1701-D", 7);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_NUMBER_OF_ISSUES.containsKey(key), TITLES_TO_NUMBER_OF_ISSUES.get(key));
	}

}
