package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TechnologyWriter implements ItemWriter<Technology> {

	private final TechnologyRepository technologyRepository;

	public TechnologyWriter(TechnologyRepository technologyRepository) {
		this.technologyRepository = technologyRepository;
	}

	@Override
	public void write(Chunk<? extends Technology> items) throws Exception {
		technologyRepository.saveAll(items.getItems());
	}

}
