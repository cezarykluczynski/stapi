package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagazineWriter implements ItemWriter<Magazine> {

	private final MagazineRepository magazineRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public MagazineWriter(MagazineRepository magazineRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.magazineRepository = magazineRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Magazine> items) throws Exception {
		magazineRepository.save(process(items));
	}

	private List<Magazine> process(List<? extends Magazine> magazineList) {
		List<Magazine> comicsListWithoutExtends = fromExtendsListToMagazineList(magazineList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<Magazine> fromExtendsListToMagazineList(List<? extends Magazine> comicsList) {
		return comicsList
				.stream()
				.map(pageAware -> (Magazine) pageAware)
				.collect(Collectors.toList());
	}

	private List<Magazine> filterDuplicates(List<Magazine> magazineList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(magazineList.stream()
				.map(magazine -> (PageAware) magazine)
				.collect(Collectors.toList()), Magazine.class).stream()
				.map(pageAware -> (Magazine) pageAware)
				.collect(Collectors.toList());
	}

}
