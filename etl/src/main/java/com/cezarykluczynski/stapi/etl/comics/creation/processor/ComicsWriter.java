package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComicsWriter implements ItemWriter<Comics> {

	private final ComicsRepository comicsRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public ComicsWriter(ComicsRepository comicsRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.comicsRepository = comicsRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Comics> items) throws Exception {
		comicsRepository.save(process(items));
	}

	private List<Comics> process(List<? extends Comics> comicsList) {
		List<Comics> comicsListWithoutExtends = fromExtendsListToComicsList(comicsList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<Comics> fromExtendsListToComicsList(List<? extends Comics> comicsList) {
		return comicsList
				.stream()
				.map(pageAware -> (Comics) pageAware)
				.collect(Collectors.toList());
	}

	private List<Comics> filterDuplicates(List<Comics> comicsList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(comicsList.stream()
				.map(comics -> (PageAware) comics)
				.collect(Collectors.toList()), Comics.class).stream()
				.map(pageAware -> (Comics) pageAware)
				.collect(Collectors.toList());
	}

}
