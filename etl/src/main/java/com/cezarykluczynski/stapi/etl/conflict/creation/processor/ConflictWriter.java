package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class ConflictWriter implements ItemWriter<Conflict> {

	private final ConflictRepository conflictRepository;

	public ConflictWriter(ConflictRepository conflictRepository) {
		this.conflictRepository = conflictRepository;
	}

	@Override
	public void write(Chunk<? extends Conflict> items) throws Exception {
		conflictRepository.saveAll(items.getItems());
	}

}
