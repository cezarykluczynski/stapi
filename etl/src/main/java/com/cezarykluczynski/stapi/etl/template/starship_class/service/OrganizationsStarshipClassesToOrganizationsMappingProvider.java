package com.cezarykluczynski.stapi.etl.template.starship_class.service;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OrganizationsStarshipClassesToOrganizationsMappingProvider {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	private final OrganizationRepository organizationRepository;

	public OrganizationsStarshipClassesToOrganizationsMappingProvider(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	static {
		MAPPINGS.put("Borg_starship_classes", "Borg Collective");
		MAPPINGS.put("Dominion_starship_classes", "Dominion");
		MAPPINGS.put("Federation_starship_classes", "United Federation of Planets");
		MAPPINGS.put("Son'a_starship_classes", "Son'a");
	}

	public Optional<Organization> provide(String starshipClassCategoryTitle) {
		if (!MAPPINGS.containsKey(starshipClassCategoryTitle)) {
			return Optional.empty();
		}

		String organizationName = MAPPINGS.get(starshipClassCategoryTitle);
		Optional<Organization> organizationOptional = organizationRepository.findByName(organizationName);

		if (!organizationOptional.isPresent()) {
			log.info("Could not find organization by name \"{}\" given in mappings", organizationName);
		}

		return organizationOptional;
	}

}
