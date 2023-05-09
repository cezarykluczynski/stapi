package com.cezarykluczynski.stapi.etl.animal.creation.processor;

import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnimalWriter implements ItemWriter<Animal> {

	private final AnimalRepository animalRepository;

	public AnimalWriter(AnimalRepository animalRepository) {
		this.animalRepository = animalRepository;
	}

	@Override
	public void write(Chunk<? extends Animal> items) throws Exception {
		animalRepository.saveAll(items.getItems());
	}

}
