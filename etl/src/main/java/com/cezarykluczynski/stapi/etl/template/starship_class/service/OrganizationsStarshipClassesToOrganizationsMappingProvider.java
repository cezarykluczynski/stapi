package com.cezarykluczynski.stapi.etl.template.starship_class.service;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationsStarshipClassesToOrganizationsMappingProvider {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final OrganizationRepository organizationRepository;

	private final Map<String, List<PageLink>> categoriesPageLinksCache = Maps.newLinkedHashMap();

	private final Map<String, Long> organizationNamesToIds = Maps.newLinkedHashMap();

	private final Set<String> loggedFailedLookups = Sets.newHashSet();

	static {
		MAPPINGS.put("Borg_starship_classes", "Borg Collective");
		MAPPINGS.put("Cardassian_starship_classes", "Cardassian Union");
		MAPPINGS.put("Confederation_starship_classes", "Confederation of Earth");
		MAPPINGS.put("Dominion_starship_classes", "Dominion");
		MAPPINGS.put("Federation_starship_classes", "United Federation of Planets");
		MAPPINGS.put("Klingon_starship_classes", "Klingon Empire");
		MAPPINGS.put("Son'a_starship_classes", "Son'a");
		MAPPINGS.put("Ferengi_starship_classes", "Ferengi Alliance");
	}

	public synchronized Optional<Organization> provide(String starshipClassCategoryTitle) {
		if (!MAPPINGS.containsKey(starshipClassCategoryTitle)) {
			List<PageLink> pageLinks = getPageLinksForCategory(starshipClassCategoryTitle);
			for (PageLink pageLink : pageLinks) {
				String pageLinkTitle = pageLink.getTitle();
				if (StringUtils.startsWithIgnoreCase(pageLinkTitle, "Category:")) {
					pageLinkTitle = pageLinkTitle.substring(9);
				}
				if (organizationNamesToIds.containsKey(pageLinkTitle)) { // Romulan Star Empire, ambiguous
					return organizationRepository.findById(organizationNamesToIds.get(pageLinkTitle));
				}
			}
			return Optional.empty();
		}

		String organizationName = MAPPINGS.get(starshipClassCategoryTitle);
		Optional<Organization> organizationOptional = organizationRepository.findByName(organizationName);

		if (!organizationOptional.isPresent()) {
			if (!loggedFailedLookups.contains(organizationName)) {
				loggedFailedLookups.add(organizationName);
				log.info("Could not find organization by name \"{}\" given in mappings.", organizationName);
			}
		}

		return organizationOptional;
	}

	private List<PageLink> getPageLinksForCategory(String starshipClassCategoryTitle) {
		if (categoriesPageLinksCache.containsKey(starshipClassCategoryTitle)) {
			return categoriesPageLinksCache.get(starshipClassCategoryTitle);
		}

		Page page = pageApi.getCategory(starshipClassCategoryTitle, MediaWikiSource.MEMORY_ALPHA_EN);
		if (page != null) {
			loadCache();
			final List<PageLink> pageLinks = wikitextApi.getPageLinksFromWikitext(page.getWikitext());
			categoriesPageLinksCache.put(starshipClassCategoryTitle, pageLinks);
			return categoriesPageLinksCache.get(starshipClassCategoryTitle);
		}

		return Lists.newArrayList();
	}

	private void loadCache() {
		if (organizationNamesToIds.isEmpty()) {
			final List<Organization> organizations = organizationRepository.findAll();
			for (Organization organization : organizations) {
				com.cezarykluczynski.stapi.model.page.entity.Page page = organization.getPage();
				if (!organizationNamesToIds.containsKey(page.getTitle())) {
					organizationNamesToIds.put(page.getTitle(), organization.getId());
				}
			}
		}
	}

}
