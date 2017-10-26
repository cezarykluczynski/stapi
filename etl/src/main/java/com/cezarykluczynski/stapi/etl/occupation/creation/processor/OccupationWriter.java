package com.cezarykluczynski.stapi.etl.occupation.creation.processor;

import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OccupationWriter implements ItemWriter<Occupation> {

	private final OccupationRepository occupationRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public OccupationWriter(OccupationRepository occupationRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.occupationRepository = occupationRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Occupation> items) throws Exception {
		occupationRepository.save(process(items));
	}

	private List<Occupation> process(List<? extends Occupation> occupationList) {
		List<Occupation> occupationListWithoutExtends = fromExtendsListToOccupationList(occupationList);
		return filterDuplicates(occupationListWithoutExtends);
	}

	private List<Occupation> fromExtendsListToOccupationList(List<? extends Occupation> occupationList) {
		return occupationList
				.stream()
				.map(pageAware -> (Occupation) pageAware)
				.collect(Collectors.toList());
	}

	private List<Occupation> filterDuplicates(List<Occupation> occupationList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(occupationList.stream()
				.map(occupation -> (PageAware) occupation)
				.collect(Collectors.toList()), Occupation.class).stream()
				.map(pageAware -> (Occupation) pageAware)
				.collect(Collectors.toList());
	}

}
