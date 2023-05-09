package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor;

import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionWriter implements ItemWriter<ComicCollection> {

	private final ComicCollectionRepository comicCollectionRepository;

	public ComicCollectionWriter(ComicCollectionRepository comicCollectionRepository) {
		this.comicCollectionRepository = comicCollectionRepository;
	}

	@Override
	public void write(Chunk<? extends ComicCollection> items) throws Exception {
		comicCollectionRepository.saveAll(items.getItems());
	}

}
