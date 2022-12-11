package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarshipClassTemplatePartOrganizationsEnrichingProcessor implements
		ItemEnrichingProcessor<EnrichablePair<Template.Part, StarshipClassTemplate>> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	@Override
	public synchronized void enrich(EnrichablePair<Template.Part, StarshipClassTemplate> enrichablePair) throws Exception {
		final Template.Part part = enrichablePair.getInput();
		final StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();
		final String key = part.getKey();
		final String value = part.getValue();

		List<Organization> organizations = wikitextToEntitiesProcessor.findOrganizations(value);
		organizations.addAll(wikitextToEntitiesProcessor.findOrganizations(part));
		if (!organizations.isEmpty()) {
			switch (key) {
				case StarshipClassTemplateParameter.OWNER:
				case StarshipClassTemplateParameter.T1OWNER:
				case StarshipClassTemplateParameter.T2OWNER:
				case StarshipClassTemplateParameter.T3OWNER:
				case StarshipClassTemplateParameter.T4OWNER:
				case StarshipClassTemplateParameter.T5OWNER:
					starshipClassTemplate.getOwners().addAll(organizations);
					break;
				case StarshipClassTemplateParameter.OPERATOR:
				case StarshipClassTemplateParameter.T1OPERATOR:
				case StarshipClassTemplateParameter.T2OPERATOR:
				case StarshipClassTemplateParameter.T3OPERATOR:
				case StarshipClassTemplateParameter.T4OPERATOR:
				case StarshipClassTemplateParameter.T5OPERATOR:
					starshipClassTemplate.getOperators().addAll(organizations);
					break;
				case StarshipClassTemplateParameter.AFFILIATION:
				case StarshipClassTemplateParameter.T1AFFILIATION:
				case StarshipClassTemplateParameter.T2AFFILIATION:
				case StarshipClassTemplateParameter.T3AFFILIATION:
				case StarshipClassTemplateParameter.T4AFFILIATION:
				case StarshipClassTemplateParameter.T5AFFILIATION:
					starshipClassTemplate.getAffiliations().addAll(organizations);
					break;
				default:
					break;
			}
		}
	}

}
