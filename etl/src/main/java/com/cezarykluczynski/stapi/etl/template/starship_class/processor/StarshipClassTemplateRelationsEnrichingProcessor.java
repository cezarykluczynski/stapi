package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StarshipClassTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipClassTemplate> {

	private final StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public StarshipClassTemplateRelationsEnrichingProcessor(StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessor,
			WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.starshipClassSpacecraftTypeProcessor = starshipClassSpacecraftTypeProcessor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();
		String starshipClassName = starshipClassTemplate.getName();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipClassTemplateParameter.OWNER:
					List<Organization> ownerList = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!ownerList.isEmpty()) {
						starshipClassTemplate.setOwner(ownerList.iterator().next());
						if (ownerList.size() > 1) {
							log.info("More than one organization found for starship class \"{}\" for owner value \"{}\", using the first value",
									starshipClassName, value);
						}
					}
					break;
				case StarshipClassTemplateParameter.OPERATOR:
					List<Organization> operatorList = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!operatorList.isEmpty()) {
						starshipClassTemplate.setOperator(operatorList.iterator().next());
						if (operatorList.size() > 1) {
							log.info("More than one organization found for starship class \"{}\" for operator value \"{}\", using the first value",
									starshipClassName, value);
						}
					}
					break;
				case StarshipClassTemplateParameter.AFFILIATION:
					List<Organization> affiliationList = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!affiliationList.isEmpty()) {
						starshipClassTemplate.setAffiliation(affiliationList.iterator().next());
						if (affiliationList.size() > 1) {
							log.info("More than one organization found for starship class {} for affiliation value {}, using the first value",
									starshipClassName, value);
						}
					}
					break;
				case StarshipClassTemplateParameter.TYPE:
					starshipClassTemplate.getSpacecraftTypes().addAll(starshipClassSpacecraftTypeProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
