package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor;

import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class ComicStripWriter implements ItemWriter<ComicStrip> {

	private final ComicStripRepository comicStripRepository;

	public ComicStripWriter(ComicStripRepository comicStripRepository) {
		this.comicStripRepository = comicStripRepository;
	}

	@Override
	public void write(Chunk<? extends ComicStrip> items) throws Exception {
		comicStripRepository.saveAll(items.getItems());
	}

}
