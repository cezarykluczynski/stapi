package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkingTemplatesToEntitiesProcessor {

	private static final Map<Class, Map<String, String>> CLASSES_TO_TEMPLATES_TO_LINKS = ImmutableMap.<Class, Map<String, String>>builder()
			.put(Organization.class, ImmutableMap.<String, String>builder()
					.put("federation", "United Federation of Planets")
					.build())
			.build();

	private final RepositoryProvider repositoryProvider;

	public <T> List<T> process(Template.Part part, Class<T> clazz) {
		List<T> findings = Lists.newArrayList();
		Map<Class, CrudRepository> classCrudRepositoryMap = repositoryProvider.provide();
		CrudRepository<T, Long> crudRepository = classCrudRepositoryMap.get(clazz);
		Map<String, String> templatesToLinks = CLASSES_TO_TEMPLATES_TO_LINKS.get(clazz);

		for (Template template : part.getTemplates()) {
			String templateTitle = template.getTitle();
			if (templatesToLinks.containsKey(templateTitle)) {
				if (crudRepository instanceof OrganizationRepository) {
					final Optional<Organization> optionalOrganization = ((OrganizationRepository) crudRepository)
							.findByName(templatesToLinks.get(templateTitle));
					if (optionalOrganization.isPresent()) {
						findings.add((T) optionalOrganization.get());
					}
				}
			}
		}
		return findings;
	}

}
