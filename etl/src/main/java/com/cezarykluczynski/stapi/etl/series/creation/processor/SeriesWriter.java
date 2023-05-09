package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SeriesWriter implements ItemWriter<Series> {

	private final SeriesRepository seriesRepository;

	public SeriesWriter(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	@Override
	public void write(Chunk<? extends Series> items) throws Exception {
		seriesRepository.saveAll(items.getItems());
	}

}
