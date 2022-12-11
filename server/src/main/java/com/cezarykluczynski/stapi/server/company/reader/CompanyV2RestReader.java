package com.cezarykluczynski.stapi.server.company.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper;
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanyV2RestReader implements BaseReader<CompanyV2RestBeanParams, CompanyV2BaseResponse>, FullReader<String, CompanyV2FullResponse> {

	private final CompanyRestQuery companyRestQuery;

	private final CompanyBaseRestMapper companyBaseRestMapper;

	private final CompanyFullRestMapper companyFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public CompanyV2RestReader(CompanyRestQuery companyRestQuery, CompanyBaseRestMapper companyBaseRestMapper,
			CompanyFullRestMapper companyFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.companyRestQuery = companyRestQuery;
		this.companyBaseRestMapper = companyBaseRestMapper;
		this.companyFullRestMapper = companyFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public CompanyV2BaseResponse readBase(CompanyV2RestBeanParams input) {
		Page<Company> companyV2Page = companyRestQuery.query(input);
		CompanyV2BaseResponse companyV2Response = new CompanyV2BaseResponse();
		companyV2Response.setPage(pageMapper.fromPageToRestResponsePage(companyV2Page));
		companyV2Response.setSort(sortMapper.map(input.getSort()));
		companyV2Response.getCompanies().addAll(companyBaseRestMapper.mapV2Base(companyV2Page.getContent()));
		return companyV2Response;
	}

	@Override
	public CompanyV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		CompanyV2RestBeanParams companyV2RestBeanParams = new CompanyV2RestBeanParams();
		companyV2RestBeanParams.setUid(uid);
		Page<Company> companyPage = companyRestQuery.query(companyV2RestBeanParams);
		CompanyV2FullResponse companyV2Response = new CompanyV2FullResponse();
		companyV2Response.setCompany(companyFullRestMapper.mapV2Full(Iterables.getOnlyElement(companyPage.getContent(), null)));
		return companyV2Response;
	}

}
