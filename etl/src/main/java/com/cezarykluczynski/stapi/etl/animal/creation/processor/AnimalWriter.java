package com.cezarykluczynski.stapi.etl.animal.creation.processor;


import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnimalWriter implements ItemWriter<Animal> {

	private final AnimalRepository animalRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public AnimalWriter(AnimalRepository animalRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.animalRepository = animalRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Animal> items) throws Exception {
		animalRepository.save(process(items));
	}

	private List<Animal> process(List<? extends Animal> animalList) {
		List<Animal> animalListWithoutExtends = fromExtendsListToAnimalList(animalList);
		return filterDuplicates(animalListWithoutExtends);
	}

	private List<Animal> fromExtendsListToAnimalList(List<? extends Animal> animalList) {
		return animalList
				.stream()
				.map(pageAware -> (Animal) pageAware)
				.collect(Collectors.toList());
	}

	private List<Animal> filterDuplicates(List<Animal> animalList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(animalList.stream()
				.map(animal -> (PageAware) animal)
				.collect(Collectors.toList()), Animal.class).stream()
				.map(pageAware -> (Animal) pageAware)
				.collect(Collectors.toList());
	}

}
