package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StarshipClassTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipClassTemplate>> {

	private final StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor;

	@Inject
	public StarshipClassTemplateRelationsEnrichingProcessor(StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor) {
		this.starshipClassSpacecraftTypeProcessor = starshipClassSpacecraftTypeProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipClassTemplateParameter.OWNER:
					// TODO
					break;
				case StarshipClassTemplateParameter.OPERATOR:
					// TODO
					break;
				case StarshipClassTemplateParameter.TYPE:
					starshipClassTemplate.setSpacecraftType(starshipClassSpacecraftTypeProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
