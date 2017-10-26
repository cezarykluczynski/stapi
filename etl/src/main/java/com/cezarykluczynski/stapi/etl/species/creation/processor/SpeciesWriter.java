package com.cezarykluczynski.stapi.etl.species.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeciesWriter implements ItemWriter<Species> {

	private final SpeciesRepository speciesRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public SpeciesWriter(SpeciesRepository speciesRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.speciesRepository = speciesRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Species> items) throws Exception {
		speciesRepository.save(process(items));
	}

	private List<Species> process(List<? extends Species> speciesList) {
		List<Species> speciesListWithoutExtends = fromExtendsListToSpeciesList(speciesList);
		return filterDuplicates(speciesListWithoutExtends);
	}

	private List<Species> fromExtendsListToSpeciesList(List<? extends Species> speciesList) {
		return speciesList
				.stream()
				.map(pageAware -> (Species) pageAware)
				.collect(Collectors.toList());
	}

	private List<Species> filterDuplicates(List<Species> speciesList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(speciesList.stream()
				.map(species -> (PageAware) species)
				.collect(Collectors.toList()), Species.class).stream()
				.map(pageAware -> (Species) pageAware)
				.collect(Collectors.toList());
	}

}
