package com.cezarykluczynski.stapi.etl.literature.creation.processor;


import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LiteratureWriter implements ItemWriter<Literature> {

	private final LiteratureRepository literatureRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public LiteratureWriter(LiteratureRepository literatureRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.literatureRepository = literatureRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Literature> items) throws Exception {
		literatureRepository.save(process(items));
	}

	private List<Literature> process(List<? extends Literature> literatureList) {
		List<Literature> literatureListWithoutExtends = fromExtendsListToLiteratureList(literatureList);
		return filterDuplicates(literatureListWithoutExtends);
	}

	private List<Literature> fromExtendsListToLiteratureList(List<? extends Literature> literatureList) {
		return literatureList
				.stream()
				.map(pageAware -> (Literature) pageAware)
				.collect(Collectors.toList());
	}

	private List<Literature> filterDuplicates(List<Literature> literatureList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(literatureList.stream()
				.map(literature -> (PageAware) literature)
				.collect(Collectors.toList()), Literature.class).stream()
				.map(pageAware -> (Literature) pageAware)
				.collect(Collectors.toList());
	}

}
