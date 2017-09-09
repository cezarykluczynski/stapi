package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StarshipTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	private final StarshipRegistryProcessor starshipRegistryProcessor;

	private final StatusProcessor statusProcessor;

	private final DateStatusProcessor dateStatusProcessor;

	public StarshipTemplateContentsEnrichingProcessor(StarshipRegistryProcessor starshipRegistryProcessor,
			StatusProcessor statusProcessor, DateStatusProcessor dateStatusProcessor) {
		this.starshipRegistryProcessor = starshipRegistryProcessor;
		this.statusProcessor = statusProcessor;
		this.dateStatusProcessor = dateStatusProcessor;
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
					starshipTemplate.setStatus(statusProcessor.process(value));
					break;
				case StarshipTemplateParameter.DATE_STATUS:
					starshipTemplate.setDateStatus(dateStatusProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
