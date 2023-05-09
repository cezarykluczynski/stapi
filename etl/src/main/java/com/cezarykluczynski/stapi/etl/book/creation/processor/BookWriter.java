package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class BookWriter implements ItemWriter<Book> {

	private final BookRepository bookRepository;

	public BookWriter(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void write(Chunk<? extends Book> items) throws Exception {
		bookRepository.saveAll(items.getItems());
	}

}
