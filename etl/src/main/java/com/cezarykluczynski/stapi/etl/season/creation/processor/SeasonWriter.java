package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonWriter implements ItemWriter<Season> {

	private final SeasonRepository seasonRepository;

	public SeasonWriter(SeasonRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@Override
	public void write(Chunk<? extends Season> items) throws Exception {
		seasonRepository.saveAll(process(items));
	}

	private List<Season> process(Chunk<? extends Season> seasonList) {
		return fromGenericsListToSeasonList(seasonList);
	}

	private List<Season> fromGenericsListToSeasonList(Chunk<? extends Season> seasonList) {
		return seasonList
				.getItems()
				.stream()
				.map(pageAware -> (Season) pageAware)
				.collect(Collectors.toList());
	}

}
