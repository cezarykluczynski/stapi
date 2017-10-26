package com.cezarykluczynski.stapi.server.organization.query;

import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRestQuery {

	private final OrganizationBaseRestMapper organizationBaseRestMapper;

	private final PageMapper pageMapper;

	private final OrganizationRepository organizationRepository;

	public OrganizationRestQuery(OrganizationBaseRestMapper organizationBaseRestMapper, PageMapper pageMapper,
			OrganizationRepository organizationRepository) {
		this.organizationBaseRestMapper = organizationBaseRestMapper;
		this.pageMapper = pageMapper;
		this.organizationRepository = organizationRepository;
	}

	public Page<Organization> query(OrganizationRestBeanParams organizationRestBeanParams) {
		OrganizationRequestDTO organizationRequestDTO = organizationBaseRestMapper.mapBase(organizationRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(organizationRestBeanParams);
		return organizationRepository.findMatching(organizationRequestDTO, pageRequest);
	}

}
