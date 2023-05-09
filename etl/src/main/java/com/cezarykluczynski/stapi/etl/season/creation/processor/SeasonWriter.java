package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SeasonWriter implements ItemWriter<Season> {

	private final SeasonRepository seasonRepository;

	public SeasonWriter(SeasonRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@Override
	public void write(Chunk<? extends Season> items) throws Exception {
		seasonRepository.saveAll(items.getItems());
	}

}
