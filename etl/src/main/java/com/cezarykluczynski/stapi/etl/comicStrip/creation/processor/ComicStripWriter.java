package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor;


import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComicStripWriter implements ItemWriter<ComicStrip> {

	private final ComicStripRepository comicStripRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	@Inject
	public ComicStripWriter(ComicStripRepository comicStripRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.comicStripRepository = comicStripRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends ComicStrip> items) throws Exception {
		comicStripRepository.save(process(items));
	}

	private List<ComicStrip> process(List<? extends ComicStrip> comicStripList) {
		List<ComicStrip> comicStripListWithoutExtends = fromExtendsListToComicStripList(comicStripList);
		return filterDuplicates(comicStripListWithoutExtends);
	}

	private List<ComicStrip> fromExtendsListToComicStripList(List<? extends ComicStrip> comicStripList) {
		return comicStripList
				.stream()
				.map(pageAware -> (ComicStrip) pageAware)
				.collect(Collectors.toList());
	}

	private List<ComicStrip> filterDuplicates(List<ComicStrip> comicStripList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(comicStripList.stream()
				.map(comicStrip -> (PageAware) comicStrip)
				.collect(Collectors.toList()), ComicStrip.class).stream()
				.map(pageAware -> (ComicStrip) pageAware)
				.collect(Collectors.toList());
	}

}
