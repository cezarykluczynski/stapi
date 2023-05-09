package com.cezarykluczynski.stapi.etl.comic_series.creation.processor;

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesWriter implements ItemWriter<ComicSeries> {

	private final ComicSeriesRepository comicSeriesRepository;


	public ComicSeriesWriter(ComicSeriesRepository comicSeriesRepository) {
		this.comicSeriesRepository = comicSeriesRepository;
	}

	@Override
	public void write(Chunk<? extends ComicSeries> items) throws Exception {
		comicSeriesRepository.saveAll(items.getItems());
	}

}
