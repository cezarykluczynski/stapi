package com.cezarykluczynski.stapi.etl.species.creation.processor;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SpeciesWriter implements ItemWriter<Species> {

	private final SpeciesRepository speciesRepository;

	public SpeciesWriter(SpeciesRepository speciesRepository) {
		this.speciesRepository = speciesRepository;
	}

	@Override
	public void write(Chunk<? extends Species> items) throws Exception {
		speciesRepository.saveAll(items.getItems());
	}

}
