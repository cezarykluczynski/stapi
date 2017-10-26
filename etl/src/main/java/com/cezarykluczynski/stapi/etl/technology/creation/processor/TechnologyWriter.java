package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TechnologyWriter implements ItemWriter<Technology> {

	private final TechnologyRepository technologyRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public TechnologyWriter(TechnologyRepository technologyRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.technologyRepository = technologyRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Technology> items) throws Exception {
		technologyRepository.save(process(items));
	}

	private List<Technology> process(List<? extends Technology> technologyList) {
		List<Technology> technologyListWithoutExtends = fromExtendsListToTechnologyList(technologyList);
		return filterDuplicates(technologyListWithoutExtends);
	}

	private List<Technology> fromExtendsListToTechnologyList(List<? extends Technology> technologyList) {
		return technologyList
				.stream()
				.map(pageAware -> (Technology) pageAware)
				.collect(Collectors.toList());
	}

	private List<Technology> filterDuplicates(List<Technology> technologyList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(technologyList.stream()
				.map(technology -> (PageAware) technology)
				.collect(Collectors.toList()), Technology.class).stream()
				.map(pageAware -> (Technology) pageAware)
				.collect(Collectors.toList());
	}

}
