package com.cezarykluczynski.stapi.server.organization.reader;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper;
import com.cezarykluczynski.stapi.server.organization.query.OrganizationSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSoapReader implements BaseReader<OrganizationBaseRequest, OrganizationBaseResponse>,
		FullReader<OrganizationFullRequest, OrganizationFullResponse> {

	private final OrganizationSoapQuery organizationSoapQuery;

	private final OrganizationBaseSoapMapper organizationBaseSoapMapper;

	private final OrganizationFullSoapMapper organizationFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public OrganizationSoapReader(OrganizationSoapQuery organizationSoapQuery, OrganizationBaseSoapMapper organizationBaseSoapMapper,
			OrganizationFullSoapMapper organizationFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.organizationSoapQuery = organizationSoapQuery;
		this.organizationBaseSoapMapper = organizationBaseSoapMapper;
		this.organizationFullSoapMapper = organizationFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public OrganizationBaseResponse readBase(OrganizationBaseRequest input) {
		Page<Organization> organizationPage = organizationSoapQuery.query(input);
		OrganizationBaseResponse organizationResponse = new OrganizationBaseResponse();
		organizationResponse.setPage(pageMapper.fromPageToSoapResponsePage(organizationPage));
		organizationResponse.setSort(sortMapper.map(input.getSort()));
		organizationResponse.getOrganizations().addAll(organizationBaseSoapMapper.mapBase(organizationPage.getContent()));
		return organizationResponse;
	}

	@Override
	public OrganizationFullResponse readFull(OrganizationFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Organization> organizationPage = organizationSoapQuery.query(input);
		OrganizationFullResponse organizationFullResponse = new OrganizationFullResponse();
		organizationFullResponse.setOrganization(organizationFullSoapMapper
				.mapFull(Iterables.getOnlyElement(organizationPage.getContent(), null)));
		return organizationFullResponse;
	}

}
