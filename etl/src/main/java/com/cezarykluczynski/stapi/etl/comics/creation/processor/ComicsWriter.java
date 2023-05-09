package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class ComicsWriter implements ItemWriter<Comics> {

	private final ComicsRepository comicsRepository;

	public ComicsWriter(ComicsRepository comicsRepository) {
		this.comicsRepository = comicsRepository;
	}

	@Override
	public void write(Chunk<? extends Comics> items) throws Exception {
		comicsRepository.saveAll(items.getItems());
	}

}
