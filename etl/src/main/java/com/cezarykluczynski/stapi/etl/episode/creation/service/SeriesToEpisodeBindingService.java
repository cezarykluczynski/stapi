package com.cezarykluczynski.stapi.etl.episode.creation.service;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeriesToEpisodeBindingService {

	private final SeriesRepository seriesRepository;

	public Series mapAbbreviationToSeries(String abbreviation) {
		return seriesRepository.findByAbbreviation(abbreviation).orElse(null);
	}

}
