package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TitleWriter implements ItemWriter<Title> {

	private final TitleRepository titleRepository;

	public TitleWriter(TitleRepository titleRepository) {
		this.titleRepository = titleRepository;
	}

	@Override
	public void write(Chunk<? extends Title> items) throws Exception {
		titleRepository.saveAll(items.getItems());
	}

}
