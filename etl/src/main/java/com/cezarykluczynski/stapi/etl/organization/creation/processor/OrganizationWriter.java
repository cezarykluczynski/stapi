package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganizationWriter implements ItemWriter<Organization> {

	private final OrganizationRepository organizationRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public OrganizationWriter(OrganizationRepository organizationRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.organizationRepository = organizationRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(Chunk<? extends Organization> items) throws Exception {
		organizationRepository.saveAll(process(items));
	}

	private List<Organization> process(Chunk<? extends Organization> organizationList) {
		List<Organization> organizationListWithoutExtends = fromExtendsListToOrganizationList(organizationList);
		return filterDuplicates(organizationListWithoutExtends);
	}

	private List<Organization> fromExtendsListToOrganizationList(Chunk<? extends Organization> organizationList) {
		return organizationList
				.getItems()
				.stream()
				.map(pageAware -> (Organization) pageAware)
				.collect(Collectors.toList());
	}

	private List<Organization> filterDuplicates(List<Organization> organizationList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(organizationList.stream()
				.map(organization -> (PageAware) organization)
				.collect(Collectors.toList()), Organization.class).stream()
				.map(pageAware -> (Organization) pageAware)
				.collect(Collectors.toList());
	}

}
