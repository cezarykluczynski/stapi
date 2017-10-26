package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpacecraftWriter implements ItemWriter<Spacecraft> {

	private final SpacecraftRepository spacecraftRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public SpacecraftWriter(SpacecraftRepository spacecraftRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.spacecraftRepository = spacecraftRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Spacecraft> items) throws Exception {
		spacecraftRepository.save(process(items));
	}

	private List<Spacecraft> process(List<? extends Spacecraft> planetList) {
		List<Spacecraft> spacecraftListWithoutExtends = fromExtendsListToSpacecraftList(planetList);
		return filterDuplicates(spacecraftListWithoutExtends);
	}

	private List<Spacecraft> fromExtendsListToSpacecraftList(List<? extends Spacecraft> planetList) {
		return planetList
				.stream()
				.map(pageAware -> (Spacecraft) pageAware)
				.collect(Collectors.toList());
	}

	private List<Spacecraft> filterDuplicates(List<Spacecraft> spacecraftList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(spacecraftList.stream()
				.map(spacecraft -> (PageAware) spacecraft)
				.collect(Collectors.toList()), Spacecraft.class).stream()
				.map(pageAware -> (Spacecraft) pageAware)
				.collect(Collectors.toList());
	}

}
