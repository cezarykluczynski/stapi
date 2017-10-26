package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.page.service.DuplicateReattachingPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformerWriter implements ItemWriter<Performer> {

	private final PerformerRepository performerRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	private final DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter;

	public PerformerWriter(PerformerRepository performerRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor,
			DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter) {
		this.performerRepository = performerRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
		this.duplicateReattachingPreSavePageAwareFilter = duplicateReattachingPreSavePageAwareFilter;
	}

	@Override
	public void write(List<? extends Performer> items) throws Exception {
		performerRepository.save(process(items));
	}

	private List<Performer> process(List<? extends Performer> performerList) {
		List<Performer> performerListWithoutExtends = fromExtendsListToPerformerList(performerList);
		List<Performer> performerListWithoutDuplicates = filterDuplicates(performerListWithoutExtends);
		List<Performer> performerListWithAttachedPages = reattach(performerListWithoutDuplicates);
		return filterDuplicates(performerListWithAttachedPages);
	}

	private List<Performer> fromExtendsListToPerformerList(List<? extends Performer> performerList) {
		return performerList
				.stream()
				.map(pageAware -> (Performer) pageAware)
				.collect(Collectors.toList());
	}

	private List<Performer> filterDuplicates(List<Performer> performerList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(performerList.stream()
				.map(performer -> (PageAware) performer)
				.collect(Collectors.toList()), Performer.class).stream()
				.map(pageAware -> (Performer) pageAware)
				.collect(Collectors.toList());
	}

	private List<Performer> reattach(List<Performer> performerList) {
		return duplicateReattachingPreSavePageAwareFilter.process(performerList.stream()
				.map(performer -> (PageAware) performer)
				.collect(Collectors.toList()), Performer.class).stream()
				.map(pageAware -> (Performer) pageAware)
				.collect(Collectors.toList());
	}

}
