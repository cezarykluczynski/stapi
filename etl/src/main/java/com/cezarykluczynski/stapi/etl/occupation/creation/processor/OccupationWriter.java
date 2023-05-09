package com.cezarykluczynski.stapi.etl.occupation.creation.processor;

import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OccupationWriter implements ItemWriter<Occupation> {

	private final OccupationRepository occupationRepository;


	public OccupationWriter(OccupationRepository occupationRepository) {
		this.occupationRepository = occupationRepository;
	}

	@Override
	public void write(Chunk<? extends Occupation> items) throws Exception {
		occupationRepository.saveAll(items.getItems());
	}

}
