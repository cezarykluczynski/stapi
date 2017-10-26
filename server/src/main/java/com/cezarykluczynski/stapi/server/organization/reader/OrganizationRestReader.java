package com.cezarykluczynski.stapi.server.organization.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.organization.dto.OrganizationRestBeanParams;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper;
import com.cezarykluczynski.stapi.server.organization.query.OrganizationRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRestReader implements BaseReader<OrganizationRestBeanParams, OrganizationBaseResponse>,
		FullReader<String, OrganizationFullResponse> {

	private final OrganizationRestQuery organizationRestQuery;

	private final OrganizationBaseRestMapper organizationBaseRestMapper;

	private final OrganizationFullRestMapper organizationFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public OrganizationRestReader(OrganizationRestQuery organizationRestQuery, OrganizationBaseRestMapper organizationBaseRestMapper,
			OrganizationFullRestMapper organizationFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.organizationRestQuery = organizationRestQuery;
		this.organizationBaseRestMapper = organizationBaseRestMapper;
		this.organizationFullRestMapper = organizationFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public OrganizationBaseResponse readBase(OrganizationRestBeanParams input) {
		Page<Organization> organizationPage = organizationRestQuery.query(input);
		OrganizationBaseResponse organizationResponse = new OrganizationBaseResponse();
		organizationResponse.setPage(pageMapper.fromPageToRestResponsePage(organizationPage));
		organizationResponse.setSort(sortMapper.map(input.getSort()));
		organizationResponse.getOrganizations().addAll(organizationBaseRestMapper.mapBase(organizationPage.getContent()));
		return organizationResponse;
	}

	@Override
	public OrganizationFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		OrganizationRestBeanParams organizationRestBeanParams = new OrganizationRestBeanParams();
		organizationRestBeanParams.setUid(uid);
		Page<Organization> organizationPage = organizationRestQuery.query(organizationRestBeanParams);
		OrganizationFullResponse organizationResponse = new OrganizationFullResponse();
		organizationResponse.setOrganization(organizationFullRestMapper
				.mapFull(Iterables.getOnlyElement(organizationPage.getContent(), null)));
		return organizationResponse;
	}
}
