package com.cezarykluczynski.stapi.etl.literature.creation.processor;

import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LiteratureWriter implements ItemWriter<Literature> {

	private final LiteratureRepository literatureRepository;

	public LiteratureWriter(LiteratureRepository literatureRepository) {
		this.literatureRepository = literatureRepository;
	}

	@Override
	public void write(Chunk<? extends Literature> items) throws Exception {
		literatureRepository.saveAll(items.getItems());
	}

}
