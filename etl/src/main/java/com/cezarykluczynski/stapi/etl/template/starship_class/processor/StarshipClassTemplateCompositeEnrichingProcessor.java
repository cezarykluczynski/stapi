package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class StarshipClassTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipClassTemplate> {

	private final StarshipClassTemplateContentsEnrichingProcessor starshipClassTemplateContentsEnrichingProcessor;

	private final StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor;

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
