package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftTypeWriter implements ItemWriter<SpacecraftType> {

	private final SpacecraftTypeRepository spacecraftTypeRepository;

	public SpacecraftTypeWriter(SpacecraftTypeRepository spacecraftTypeRepository) {
		this.spacecraftTypeRepository = spacecraftTypeRepository;
	}

	@Override
	public void write(Chunk<? extends SpacecraftType> items) throws Exception {
		spacecraftTypeRepository.saveAll(items.getItems());
	}

}
