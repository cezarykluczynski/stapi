package com.cezarykluczynski.stapi.etl.magazine_series.creation.service;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MagazineSeriesDetector {

	private static final Set<String> MAGAZINE_SERIES_TITLE = Sets.newHashSet(
			"Fantastic Films",
			"T-Negative",
			"Star Trek: The Next Generation - The Official Poster Magazine",
			"Star Trek Generations - The Official Poster Magazine",
			"Film Score Monthly",
			"The Electric Company Magazine",
			"Stardate (magazine)",
			"Star Trek Explorer",
			"The Official Star Trek The Next Generation: Build the USS Enterprise NCC-1701-D",
			"Starburst",
			"Wired"
	);

	public boolean isMagazineSeries(Page page) {
		return MAGAZINE_SERIES_TITLE.contains(page.getTitle());
	}

}
