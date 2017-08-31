package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StarshipTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipTemplate>> {

	private final StarshipRegistryProcessor starshipRegistryProcessor;

	private final StarshipStatusProcessor starshipStatusProcessor;

	private final StarshipDateStatusProcessor starshipDateStatusProcessor;

	public StarshipTemplateContentsEnrichingProcessor(StarshipRegistryProcessor starshipRegistryProcessor,
			StarshipStatusProcessor starshipStatusProcessor, StarshipDateStatusProcessor starshipDateStatusProcessor) {
		this.starshipRegistryProcessor = starshipRegistryProcessor;
		this.starshipStatusProcessor = starshipStatusProcessor;
		this.starshipDateStatusProcessor = starshipDateStatusProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipTemplate starshipTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipTemplateParameter.REGISTRY:
					starshipTemplate.setRegistry(starshipRegistryProcessor.process(value));
					break;
				case StarshipTemplateParameter.STATUS:
					starshipTemplate.setStatus(starshipStatusProcessor.process(value));
					break;
				case StarshipTemplateParameter.DATE_STATUS:
					starshipTemplate.setDateStatus(starshipDateStatusProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
