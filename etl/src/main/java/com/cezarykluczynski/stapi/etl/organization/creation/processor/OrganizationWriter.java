package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationWriter implements ItemWriter<Organization> {

	private final OrganizationRepository organizationRepository;

	public OrganizationWriter(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public void write(Chunk<? extends Organization> items) throws Exception {
		organizationRepository.saveAll(items.getItems());
	}

}
