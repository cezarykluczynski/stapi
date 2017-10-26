package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeasonSeriesProcessor implements ItemProcessor<String, Series> {

	private final SeriesRepository seriesRepository;

	public SeasonSeriesProcessor(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	@Override
	public Series process(String item) throws Exception {
		String abbreviation = StringUtils.substringBefore(item, " ");
		Optional<Series> seriesOptional = seriesRepository.findByAbbreviation(abbreviation);

		if (seriesOptional.isPresent()) {
			return seriesOptional.get();
		} else {
			throw new StapiRuntimeException(String.format("Could not get series for page %s using abbreviation %s", item, abbreviation));
		}
	}

}
