package com.cezarykluczynski.stapi.etl.book_collection.creation.processor;

import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionWriter implements ItemWriter<BookCollection> {

	private final BookCollectionRepository bookCollectionRepository;

	public BookCollectionWriter(BookCollectionRepository bookCollectionRepository) {
		this.bookCollectionRepository = bookCollectionRepository;
	}

	@Override
	public void write(Chunk<? extends BookCollection> items) throws Exception {
		bookCollectionRepository.saveAll(items.getItems());
	}

}
