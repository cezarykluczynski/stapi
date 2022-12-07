package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarshipClassTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipClassTemplate> {

	private final StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor;

	private final StarshipClassTemplatePartOrganizationsEnrichingProcessor starshipClassTemplatePartOrganizationsEnrichingProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipClassTemplateParameter.OWNER:
				case StarshipClassTemplateParameter.T1OWNER:
				case StarshipClassTemplateParameter.T2OWNER:
				case StarshipClassTemplateParameter.T3OWNER:
				case StarshipClassTemplateParameter.T4OWNER:
				case StarshipClassTemplateParameter.T5OWNER:
				case StarshipClassTemplateParameter.OPERATOR:
				case StarshipClassTemplateParameter.T1OPERATOR:
				case StarshipClassTemplateParameter.T2OPERATOR:
				case StarshipClassTemplateParameter.T3OPERATOR:
				case StarshipClassTemplateParameter.T4OPERATOR:
				case StarshipClassTemplateParameter.T5OPERATOR:
				case StarshipClassTemplateParameter.AFFILIATION:
				case StarshipClassTemplateParameter.T1AFFILIATION:
				case StarshipClassTemplateParameter.T2AFFILIATION:
				case StarshipClassTemplateParameter.T3AFFILIATION:
				case StarshipClassTemplateParameter.T4AFFILIATION:
				case StarshipClassTemplateParameter.T5AFFILIATION:
					starshipClassTemplatePartOrganizationsEnrichingProcessor.enrich(EnrichablePair.of(part, starshipClassTemplate));
					break;
				case StarshipClassTemplateParameter.TYPE:
				case StarshipClassTemplateParameter.T1TYPE:
				case StarshipClassTemplateParameter.T2TYPE:
				case StarshipClassTemplateParameter.T3TYPE:
				case StarshipClassTemplateParameter.T4TYPE:
				case StarshipClassTemplateParameter.T5TYPE:
					starshipClassTemplate.getSpacecraftTypes().addAll(starshipClassSpacecraftTypeProcessor.process(value));
					break;
				case StarshipClassTemplateParameter.ARMAMENT:
					starshipClassTemplate.getArmaments().addAll(wikitextToEntitiesProcessor.findWeapons(value));
					break;
				default:
					break;
			}
		}
	}

}
