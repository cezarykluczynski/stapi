package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.organization.WikitextToOrganizationsProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
@Slf4j
public class StarshipClassTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipClassTemplate>> {

	private final StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor;

	private final WikitextToOrganizationsProcessor wikitextToOrganizationsProcessor;

	@Inject
	public StarshipClassTemplateRelationsEnrichingProcessor(StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor,
			WikitextToOrganizationsProcessor wikitextToOrganizationsProcessor) {
		this.starshipClassSpacecraftTypeProcessor = starshipClassSpacecraftTypeProcessor;
		this.wikitextToOrganizationsProcessor = wikitextToOrganizationsProcessor;
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
					Set<Organization> ownerSet = wikitextToOrganizationsProcessor.process(value);
					if (ownerSet.size() == 1) {
						starshipClassTemplate.setOwner(ownerSet.iterator().next());
					} else if (!ownerSet.isEmpty()) {
						log.info("More than one organization found for owner value {}", value);
					}
					break;
				case StarshipClassTemplateParameter.OPERATOR:
					Set<Organization> operatorSet = wikitextToOrganizationsProcessor.process(value);
					if (operatorSet.size() == 1) {
						starshipClassTemplate.setOperator(operatorSet.iterator().next());
					} else if (!operatorSet.isEmpty()) {
						log.info("More than one organization found for operator value {}", value);
					}
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
