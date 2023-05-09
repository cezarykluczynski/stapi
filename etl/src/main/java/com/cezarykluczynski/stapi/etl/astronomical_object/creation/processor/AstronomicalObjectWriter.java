package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectWriter implements ItemWriter<AstronomicalObject> {

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	public AstronomicalObjectWriter(AstronomicalObjectRepository astronomicalObjectRepository) {
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	@Override
	public void write(Chunk<? extends AstronomicalObject> items) throws Exception {
		astronomicalObjectRepository.saveAll(items.getItems());
	}

}
