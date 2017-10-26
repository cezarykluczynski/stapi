package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AstronomicalObjectWriter implements ItemWriter<AstronomicalObject> {

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public AstronomicalObjectWriter(AstronomicalObjectRepository astronomicalObjectRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.astronomicalObjectRepository = astronomicalObjectRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends AstronomicalObject> items) throws Exception {
		astronomicalObjectRepository.save(process(items));
	}

	private List<AstronomicalObject> process(List<? extends AstronomicalObject> planetList) {
		List<AstronomicalObject> astronomicalObjectListWithoutExtends = fromExtendsListToAstronomicalObjectList(planetList);
		return filterDuplicates(astronomicalObjectListWithoutExtends);
	}

	private List<AstronomicalObject> fromExtendsListToAstronomicalObjectList(List<? extends AstronomicalObject> planetList) {
		return planetList
				.stream()
				.map(pageAware -> (AstronomicalObject) pageAware)
				.collect(Collectors.toList());
	}

	private List<AstronomicalObject> filterDuplicates(List<AstronomicalObject> astronomicalObjectList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(astronomicalObjectList.stream()
				.map(astronomicalObject -> (PageAware) astronomicalObject)
				.collect(Collectors.toList()), AstronomicalObject.class).stream()
				.map(pageAware -> (AstronomicalObject) pageAware)
				.collect(Collectors.toList());
	}

}
