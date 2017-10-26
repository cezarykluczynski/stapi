package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConflictWriter implements ItemWriter<Conflict> {

	private final ConflictRepository conflictRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public ConflictWriter(ConflictRepository conflictRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.conflictRepository = conflictRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Conflict> items) throws Exception {
		conflictRepository.save(process(items));
	}

	private List<Conflict> process(List<? extends Conflict> planetList) {
		List<Conflict> conflictListWithoutExtends = fromExtendsListToConflictList(planetList);
		return filterDuplicates(conflictListWithoutExtends);
	}

	private List<Conflict> fromExtendsListToConflictList(List<? extends Conflict> planetList) {
		return planetList
				.stream()
				.map(pageAware -> (Conflict) pageAware)
				.collect(Collectors.toList());
	}

	private List<Conflict> filterDuplicates(List<Conflict> conflictList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(conflictList.stream()
				.map(conflict -> (PageAware) conflict)
				.collect(Collectors.toList()), Conflict.class).stream()
				.map(pageAware -> (Conflict) pageAware)
				.collect(Collectors.toList());
	}

}
