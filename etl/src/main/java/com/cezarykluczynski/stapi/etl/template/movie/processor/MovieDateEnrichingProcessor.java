package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieDateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, MovieTemplate>> {

	private final MovieTemplateStardateYearFixedValueProvider movieTemplateStardateYearFixedValueProvider;

	@Override
	public void enrich(EnrichablePair<Page, MovieTemplate> enrichablePair) throws Exception {
		final String movieTitle = enrichablePair.getInput().getTitle();
		final FixedValueHolder<StardateYearDTO> stardateYearDTOFixedValueHolder = movieTemplateStardateYearFixedValueProvider
				.getSearchedValue(movieTitle);
		if (stardateYearDTOFixedValueHolder.isFound()) {
			final StardateYearDTO stardateYearDTO = stardateYearDTOFixedValueHolder.getValue();
			final MovieTemplate movieTemplate = enrichablePair.getOutput();
			movieTemplate.setStardateFrom(stardateYearDTO.getStardateFrom());
			movieTemplate.setStardateTo(stardateYearDTO.getStardateTo());
			movieTemplate.setYearFrom(stardateYearDTO.getYearFrom());
			movieTemplate.setYearTo(stardateYearDTO.getYearTo());
		} else {
			log.warn("No FixedValueHolder<StardateYearDTO> present for movie \"{}\".", movieTitle);
		}
	}

}
