package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
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
	public void write(List<? extends Season> items) throws Exception {
		seasonRepository.save(process(items));
	}

	private List<Season> process(List<? extends Season> seasonList) {
		return fromGenericsListToSeasonList(seasonList);
	}

	private List<Season> fromGenericsListToSeasonList(List<? extends Season> seasonList) {
		return seasonList
				.stream()
				.map(pageAware -> (Season) pageAware)
				.collect(Collectors.toList());
	}

}
