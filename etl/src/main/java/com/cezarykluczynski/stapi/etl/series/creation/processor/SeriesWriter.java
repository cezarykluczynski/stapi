package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SeriesWriter implements ItemWriter<Series> {

	private SeriesRepository seriesRepository;

	@Inject
	public SeriesWriter(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	@Override
	public void write(List<? extends Series> items) throws Exception {
		seriesRepository.save(items);
	}

}
