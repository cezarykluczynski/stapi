package com.cezarykluczynski.stapi.etl.organization.creation.processor;


import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationWriter implements ItemWriter<Organization> {

	private OrganizationRepository corganizationRepository;

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	@Inject
	public OrganizationWriter(OrganizationRepository corganizationRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.corganizationRepository = corganizationRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Organization> items) throws Exception {
		corganizationRepository.save(process(items));
	}

	private List<Organization> process(List<? extends Organization> corganizationList) {
		List<Organization> corganizationListWithoutExtends = fromExtendsListToOrganizationList(corganizationList);
		return filterDuplicates(corganizationListWithoutExtends);
	}

	private List<Organization> fromExtendsListToOrganizationList(List<? extends Organization> corganizationList) {
		return corganizationList
				.stream()
				.map(pageAware -> (Organization) pageAware)
				.collect(Collectors.toList());
	}

	private List<Organization> filterDuplicates(List<Organization> corganizationList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(corganizationList.stream()
				.map(corganization -> (PageAware) corganization)
				.collect(Collectors.toList()), Organization.class).stream()
				.map(pageAware -> (Organization) pageAware)
				.collect(Collectors.toList());
	}

}
