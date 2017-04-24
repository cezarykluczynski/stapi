package com.cezarykluczynski.stapi.etl.bookCollection.creation.processor;

import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.bookCollection.repository.BookCollectionRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCollectionWriter implements ItemWriter<BookCollection> {

	private BookCollectionRepository bookCollectionRepository;

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	@Inject
	public BookCollectionWriter(BookCollectionRepository bookCollectionRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.bookCollectionRepository = bookCollectionRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends BookCollection> items) throws Exception {
		bookCollectionRepository.save(process(items));
	}

	private List<BookCollection> process(List<? extends BookCollection> bookCollectionList) {
		List<BookCollection> bookCollectionListWithoutExtends = fromExtendsListToBookCollectionList(bookCollectionList);
		return filterDuplicates(bookCollectionListWithoutExtends);
	}

	private List<BookCollection> fromExtendsListToBookCollectionList(List<? extends BookCollection> bookCollectionList) {
		return bookCollectionList
				.stream()
				.map(pageAware -> (BookCollection) pageAware)
				.collect(Collectors.toList());
	}

	private List<BookCollection> filterDuplicates(List<BookCollection> bookCollectionList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(bookCollectionList.stream()
				.map(bookCollection -> (PageAware) bookCollection)
				.collect(Collectors.toList()), BookCollection.class).stream()
				.map(pageAware -> (BookCollection) pageAware)
				.collect(Collectors.toList());
	}

}
