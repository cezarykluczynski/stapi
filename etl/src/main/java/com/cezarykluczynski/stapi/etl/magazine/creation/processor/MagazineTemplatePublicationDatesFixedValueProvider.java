package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.PublicationDates;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MagazineTemplatePublicationDatesFixedValueProvider implements FixedValueProvider<String, PublicationDates> {

	private static final Map<String, PublicationDates> TITLES_TO_PUBLICATION_DATES = Maps.newHashMap();

	static {
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Fan Club Magazine issue 9", PublicationDates.of(DayMonthYear.of(null, null, null),
				DayMonthYear.of(null, null, 1996)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Fan Club Magazine issue 66", PublicationDates.of(DayMonthYear.of(null, null, null),
				DayMonthYear.of(null, 2, 1989)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Fan Club Magazine issue 67", PublicationDates.of(DayMonthYear.of(null, null, null),
				DayMonthYear.of(null, 4, 1989)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Fan Club Magazine issue 69", PublicationDates.of(DayMonthYear.of(null, null, null),
				DayMonthYear.of(null, 8, 1989)));
		TITLES_TO_PUBLICATION_DATES.put("The Star Trek Files: The Animated Voyages End", PublicationDates.of(DayMonthYear.of(null, null, 1985),
				DayMonthYear.of(null, null, null)));
	}

	@Override
	public FixedValueHolder<PublicationDates> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLICATION_DATES.containsKey(key), TITLES_TO_PUBLICATION_DATES.get(key));
	}

}
