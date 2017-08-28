package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StarshipClassTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipClassTemplate>> {

	private final StarshipClassTemplateContentsEnrichingProcessor starshipClassTemplateContentsEnrichingProcessor;

	private final StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor;

	@Inject
	public StarshipClassTemplateCompositeEnrichingProcessor(
			StarshipClassTemplateContentsEnrichingProcessor starshipClassTemplateContentsEnrichingProcessor,
			StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor) {
		this.starshipClassTemplateContentsEnrichingProcessor = starshipClassTemplateContentsEnrichingProcessor;
		this.starshipClassTemplateRelationsEnrichingProcessor = starshipClassTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		starshipClassTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		starshipClassTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}
