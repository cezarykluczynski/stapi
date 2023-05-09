package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor;

import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassWriter implements ItemWriter<SpacecraftClass> {

	private final SpacecraftClassRepository spacecraftClassRepository;

	public SpacecraftClassWriter(SpacecraftClassRepository spacecraftClassRepository) {
		this.spacecraftClassRepository = spacecraftClassRepository;
	}

	@Override
	public void write(Chunk<? extends SpacecraftClass> items) throws Exception {
		spacecraftClassRepository.saveAll(items.getItems());
	}

}
