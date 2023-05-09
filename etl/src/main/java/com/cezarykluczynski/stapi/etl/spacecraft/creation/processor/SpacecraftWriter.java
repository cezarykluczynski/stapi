package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftWriter implements ItemWriter<Spacecraft> {

	private final SpacecraftRepository spacecraftRepository;

	public SpacecraftWriter(SpacecraftRepository spacecraftRepository) {
		this.spacecraftRepository = spacecraftRepository;
	}

	@Override
	public void write(Chunk<? extends Spacecraft> items) throws Exception {
		spacecraftRepository.saveAll(items.getItems());
	}

}
