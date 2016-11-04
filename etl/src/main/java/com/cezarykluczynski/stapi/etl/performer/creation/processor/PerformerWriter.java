package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformerWriter implements ItemWriter<Performer> {

	private PerformerRepository performerRepository;

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	@Inject
	public PerformerWriter(PerformerRepository performerRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.performerRepository = performerRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Performer> items) throws Exception {
		performerRepository.save(process(items));
	}

	private List<Performer> process(List<? extends Performer> performerList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(performerList.stream()
				.map(performer -> (PageAware) performer)
				.collect(Collectors.toList()), Performer.class).stream()
				.map(pageAware -> (Performer) pageAware)
				.collect(Collectors.toList());
	}

}
