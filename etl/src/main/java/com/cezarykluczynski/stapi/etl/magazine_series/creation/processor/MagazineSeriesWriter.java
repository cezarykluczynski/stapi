package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor;

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesWriter implements ItemWriter<MagazineSeries> {

	private final MagazineSeriesRepository magazineSeriesRepository;

	public MagazineSeriesWriter(MagazineSeriesRepository magazineSeriesRepository) {
		this.magazineSeriesRepository = magazineSeriesRepository;
	}

	@Override
	public void write(Chunk<? extends MagazineSeries> items) throws Exception {
		magazineSeriesRepository.saveAll(items.getItems());
	}

}
