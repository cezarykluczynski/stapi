package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarshipClassTemplatePartOrganizationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, StarshipClassTemplate>> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final Map<String, Map<String, String>> titlesToSourceFields = Maps.newLinkedHashMap();

	private final Set<String> loggedFailedLookups = Sets.newHashSet();

	@Override
	public synchronized void enrich(EnrichablePair<Template.Part, StarshipClassTemplate> enrichablePair) throws Exception {
		final Template.Part part = enrichablePair.getInput();
		final StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();
		final String key = part.getKey();
		final String value = part.getValue();

		List<Organization> organizations = wikitextToEntitiesProcessor.findOrganizations(value);
		organizations.addAll(wikitextToEntitiesProcessor.findOrganizations(part));
		String mainKey = null;
		if (!organizations.isEmpty()) {
			Organization organization = organizations.iterator().next();
			switch (key) {
				case StarshipClassTemplateParameter.OWNER:
				case StarshipClassTemplateParameter.T1OWNER:
				case StarshipClassTemplateParameter.T2OWNER:
				case StarshipClassTemplateParameter.T3OWNER:
				case StarshipClassTemplateParameter.T4OWNER:
				case StarshipClassTemplateParameter.T5OWNER:
					mainKey = StarshipClassTemplateParameter.OWNER;
					setIfNotAlreadySet(mainKey, key, starshipClassTemplate, organization, StarshipClassTemplate::getOwner, StarshipClassTemplate::setOwner);
					break;
				case StarshipClassTemplateParameter.OPERATOR:
				case StarshipClassTemplateParameter.T1OPERATOR:
				case StarshipClassTemplateParameter.T2OPERATOR:
				case StarshipClassTemplateParameter.T3OPERATOR:
				case StarshipClassTemplateParameter.T4OPERATOR:
				case StarshipClassTemplateParameter.T5OPERATOR:
					mainKey = StarshipClassTemplateParameter.OPERATOR;
					setIfNotAlreadySet(mainKey, key, starshipClassTemplate, organization, StarshipClassTemplate::getOperator, StarshipClassTemplate::setOperator);
					break;
				case StarshipClassTemplateParameter.AFFILIATION:
				case StarshipClassTemplateParameter.T1AFFILIATION:
				case StarshipClassTemplateParameter.T2AFFILIATION:
				case StarshipClassTemplateParameter.T3AFFILIATION:
				case StarshipClassTemplateParameter.T4AFFILIATION:
				case StarshipClassTemplateParameter.T5AFFILIATION:
					mainKey = StarshipClassTemplateParameter.AFFILIATION;
					setIfNotAlreadySet(mainKey, key, starshipClassTemplate, organization, StarshipClassTemplate::getAffiliation, StarshipClassTemplate::setAffiliation);
					break;
				default:
					break;
			}
			
			if (organizations.size() > 1) {
				String starshipClassName = starshipClassTemplate.getName();
				String loggingKey = String.format("%s-%s", starshipClassName, mainKey);
				if (!loggedFailedLookups.contains(loggingKey)) {
					loggedFailedLookups.add(loggingKey);
					// TODO: consider one to many relation, as this happens more than once
					log.info("More than one organization found for starship class \"{}\" for {} value \"{}\", " +
							"using the first value, found organization were: {}.", starshipClassName, mainKey, value, organizations.stream()
							.map(Organization::getName)
							.collect(Collectors.toList()));
				}
			}
		}
	}

	private void setIfNotAlreadySet(String mainKey, String currentKey, StarshipClassTemplate starshipClassTemplate, Organization foundOrganization,
			Function<StarshipClassTemplate, Organization> currentValueGetter, BiConsumer<StarshipClassTemplate, Organization> setter) {
		final String title = starshipClassTemplate.getPage().getTitle();
		if (!titlesToSourceFields.containsKey(title)) {
			titlesToSourceFields.put(title, Maps.newLinkedHashMap());
		}
		Map<String, String> sourceFields = titlesToSourceFields.get(title);
		Organization currentValue = currentValueGetter.apply(starshipClassTemplate);
		if (currentValue == null) {
			setter.accept(starshipClassTemplate, foundOrganization);
			sourceFields.put(mainKey, currentKey);
		} else if (sourceFields.containsKey(mainKey) && !currentValue.equals(foundOrganization)) {
			log.info("Operator for starship class template {} was already set by key {} to {}, would be set by key {} to {}.",
					title, sourceFields.get(mainKey), currentValue.getName(), currentKey, foundOrganization.getName());
		}
	}

}
