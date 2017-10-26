package com.cezarykluczynski.stapi.server.organization.query;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.model.organization.dto.OrganizationRequestDTO;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSoapQuery {

	private final OrganizationBaseSoapMapper organizationBaseSoapMapper;

	private final OrganizationFullSoapMapper organizationFullSoapMapper;

	private final PageMapper pageMapper;

	private final OrganizationRepository organizationRepository;

	public OrganizationSoapQuery(OrganizationBaseSoapMapper organizationBaseSoapMapper, OrganizationFullSoapMapper organizationFullSoapMapper,
			PageMapper pageMapper, OrganizationRepository organizationRepository) {
		this.organizationBaseSoapMapper = organizationBaseSoapMapper;
		this.organizationFullSoapMapper = organizationFullSoapMapper;
		this.pageMapper = pageMapper;
		this.organizationRepository = organizationRepository;
	}

	public Page<Organization> query(OrganizationBaseRequest organizationBaseRequest) {
		OrganizationRequestDTO organizationRequestDTO = organizationBaseSoapMapper.mapBase(organizationBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(organizationBaseRequest.getPage());
		return organizationRepository.findMatching(organizationRequestDTO, pageRequest);
	}

	public Page<Organization> query(OrganizationFullRequest organizationFullRequest) {
		OrganizationRequestDTO seriesRequestDTO = organizationFullSoapMapper.mapFull(organizationFullRequest);
		return organizationRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
