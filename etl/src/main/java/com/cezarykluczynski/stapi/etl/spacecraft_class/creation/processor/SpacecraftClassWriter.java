package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpacecraftClassWriter implements ItemWriter<SpacecraftClass> {

	private final SpacecraftClassRepository spacecraftClassRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public SpacecraftClassWriter(SpacecraftClassRepository spacecraftClassRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.spacecraftClassRepository = spacecraftClassRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends SpacecraftClass> items) throws Exception {
		spacecraftClassRepository.save(process(items));
	}

	private List<SpacecraftClass> process(List<? extends SpacecraftClass> planetList) {
		List<SpacecraftClass> spacecraftClassListWithoutExtends = fromExtendsListToSpacecraftClassList(planetList);
		return filterDuplicates(spacecraftClassListWithoutExtends);
	}

	private List<SpacecraftClass> fromExtendsListToSpacecraftClassList(List<? extends SpacecraftClass> planetList) {
		return planetList
				.stream()
				.map(pageAware -> (SpacecraftClass) pageAware)
				.collect(Collectors.toList());
	}

	private List<SpacecraftClass> filterDuplicates(List<SpacecraftClass> spacecraftClassList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(spacecraftClassList.stream()
				.map(spacecraftClass -> (PageAware) spacecraftClass)
				.collect(Collectors.toList()), SpacecraftClass.class).stream()
				.map(pageAware -> (SpacecraftClass) pageAware)
				.collect(Collectors.toList());
	}

}
