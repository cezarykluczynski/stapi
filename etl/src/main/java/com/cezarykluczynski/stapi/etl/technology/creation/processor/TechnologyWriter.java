package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
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
	public void write(Chunk<? extends Technology> items) throws Exception {
		technologyRepository.saveAll(process(items));
	}

	private List<Technology> process(Chunk<? extends Technology> technologyList) {
		List<Technology> technologyListWithoutExtends = fromExtendsListToTechnologyList(technologyList);
		return filterDuplicates(technologyListWithoutExtends);
	}

	private List<Technology> fromExtendsListToTechnologyList(Chunk<? extends Technology> technologyList) {
		return technologyList
				.getItems()
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
