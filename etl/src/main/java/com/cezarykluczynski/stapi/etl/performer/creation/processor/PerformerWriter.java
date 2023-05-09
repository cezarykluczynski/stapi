package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class PerformerWriter implements ItemWriter<Performer> {

	private final PerformerRepository performerRepository;

	public PerformerWriter(PerformerRepository performerRepository) {
		this.performerRepository = performerRepository;
	}

	@Override
	public void write(Chunk<? extends Performer> items) throws Exception {
		performerRepository.saveAll(items.getItems());
	}

}
