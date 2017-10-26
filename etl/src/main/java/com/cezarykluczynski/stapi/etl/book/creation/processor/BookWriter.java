package com.cezarykluczynski.stapi.etl.book.creation.processor;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
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
	public void write(List<? extends Book> items) throws Exception {
		bookRepository.save(process(items));
	}

	private List<Book> process(List<? extends Book> bookList) {
		List<Book> bookListWithoutExtends = fromExtendsListToBookList(bookList);
		return filterDuplicates(bookListWithoutExtends);
	}

	private List<Book> fromExtendsListToBookList(List<? extends Book> bookList) {
		return bookList
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
