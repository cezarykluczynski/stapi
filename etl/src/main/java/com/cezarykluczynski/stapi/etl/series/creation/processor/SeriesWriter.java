package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesWriter implements ItemWriter<Series> {

	private final SeriesRepository seriesRepository;

	public SeriesWriter(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	@Override
	public void write(List<? extends Series> items) throws Exception {
		seriesRepository.save(process(items));
	}

	private List<Series> process(List<? extends Series> seriesList) {
		return fromGenericsListToSeriesList(seriesList);
	}

	private List<Series> fromGenericsListToSeriesList(List<? extends Series> seriesList) {
		return seriesList
				.stream()
				.map(pageAware -> (Series) pageAware)
				.collect(Collectors.toList());
	}

}
