package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.service.OrganizationsStarshipClassesToOrganizationsMappingProvider;
import com.cezarykluczynski.stapi.etl.template.starship_class.service.SpeciesStarshipClassesToSpeciesMappingProvider;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StarshipClassTemplateCategoriesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<CategoryHeader>, StarshipClassTemplate>> {

	private static final String ALTERNATE_REALITY = "alternate_reality";

	private final SpeciesStarshipClassesToSpeciesMappingProvider speciesStarshipClassesToSpeciesMappingProvider;

	private final OrganizationsStarshipClassesToOrganizationsMappingProvider organizationsStarshipClassesToOrganizationsMappingProvider;

	public StarshipClassTemplateCategoriesEnrichingProcessor(
			SpeciesStarshipClassesToSpeciesMappingProvider speciesStarshipClassesToSpeciesMappingProvider,
			OrganizationsStarshipClassesToOrganizationsMappingProvider organizationsStarshipClassesToOrganizationsMappingProvider) {
		this.speciesStarshipClassesToSpeciesMappingProvider = speciesStarshipClassesToSpeciesMappingProvider;
		this.organizationsStarshipClassesToOrganizationsMappingProvider = organizationsStarshipClassesToOrganizationsMappingProvider;
	}

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, StarshipClassTemplate> enrichablePair) throws Exception {
		List<Species> speciesCandidatesList = Lists.newArrayList();
		List<Organization> organizationCandidatesList = Lists.newArrayList();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();
		List<CategoryHeader> categoryHeaderList = enrichablePair.getInput();

		categoryHeaderList.forEach(categoryHeader -> {
			String title = categoryHeader.getTitle();
			speciesStarshipClassesToSpeciesMappingProvider.provide(title).ifPresent(speciesCandidatesList::add);
			organizationsStarshipClassesToOrganizationsMappingProvider.provide(title).ifPresent(organizationCandidatesList::add);
			if (StringUtils.contains(title, ALTERNATE_REALITY)) {
				starshipClassTemplate.setAlternateReality(true);
			}
		});

		if (speciesCandidatesList.size() == 1) {
			starshipClassTemplate.setSpecies(speciesCandidatesList.get(0));
		} else if (!speciesCandidatesList.isEmpty()) {
			log.warn("More than one species found for starship class \"{}\" when categories were looked at, none was used",
					starshipClassTemplate.getName());
		}

		if (organizationCandidatesList.size() == 1) {
			starshipClassTemplate.setAffiliation(organizationCandidatesList.get(0));
		} else if (!organizationCandidatesList.isEmpty()) {
			log.warn("More than one organization found for starship class \"{}\" when categories were looked at, none was used",
					starshipClassTemplate.getName());
		}
	}

}
