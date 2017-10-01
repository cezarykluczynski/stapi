package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacecraftCategoriesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<String>, StarshipTemplate>> {

	public void enrich(EnrichablePair<List<String>, StarshipTemplate> enrichablePair) {
		// TODO
	}

}
