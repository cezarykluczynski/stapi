package com.cezarykluczynski.stapi.etl.template.spacecraft.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpacecraftTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	private final SpacecraftRegistryProcessor spacecraftRegistryProcessor;

	private final StatusProcessor statusProcessor;

	private final DateStatusProcessor dateStatusProcessor;

	public SpacecraftTemplateContentsEnrichingProcessor(SpacecraftRegistryProcessor spacecraftRegistryProcessor,
			StatusProcessor statusProcessor, DateStatusProcessor dateStatusProcessor) {
		this.spacecraftRegistryProcessor = spacecraftRegistryProcessor;
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
					starshipTemplate.setRegistry(spacecraftRegistryProcessor.process(value));
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
