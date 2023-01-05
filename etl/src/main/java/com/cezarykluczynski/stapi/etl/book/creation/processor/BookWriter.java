package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookWriter implements ItemWriter<Book> {

	private final BookRepository bookRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public BookWriter(BookRepository bookRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.bookRepository = bookRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(Chunk<? extends Book> items) throws Exception {
		bookRepository.saveAll(process(items));
	}

	private List<Book> process(Chunk<? extends Book> bookList) {
		List<Book> bookListWithoutExtends = fromExtendsListToBookList(bookList);
		return filterDuplicates(bookListWithoutExtends);
	}

	private List<Book> fromExtendsListToBookList(Chunk<? extends Book> bookList) {
		return bookList
				.getItems()
				.stream()
				.map(pageAware -> (Book) pageAware)
				.collect(Collectors.toList());
	}

	private List<Book> filterDuplicates(List<Book> bookList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(bookList.stream()
				.map(book -> (PageAware) book)
				.collect(Collectors.toList()), Book.class).stream()
				.map(pageAware -> (Book) pageAware)
				.collect(Collectors.toList());
	}

}
