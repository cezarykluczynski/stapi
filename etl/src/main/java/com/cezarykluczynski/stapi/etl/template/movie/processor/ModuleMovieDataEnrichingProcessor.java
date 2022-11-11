package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ModuleMovieDataEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<ModuleEpisodeData, MovieTemplate>> {

	@Override
	public void enrich(EnrichablePair<ModuleEpisodeData, MovieTemplate> enrichablePair) throws Exception {
		final ModuleEpisodeData moduleEpisodeData = enrichablePair.getInput();
		final MovieTemplate movieTemplate = enrichablePair.getOutput();
		if (moduleEpisodeData != null
				&& moduleEpisodeData.getReleaseDay() != null
				&& moduleEpisodeData.getReleaseMonth() != null
				&& moduleEpisodeData.getReleaseYear() != null) {
			movieTemplate.setUsReleaseDate(LocalDate.of(moduleEpisodeData.getReleaseYear(), moduleEpisodeData.getReleaseMonth(),
					moduleEpisodeData.getReleaseDay()));
		}
	}

}
