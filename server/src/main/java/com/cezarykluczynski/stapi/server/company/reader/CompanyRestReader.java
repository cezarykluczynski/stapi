package com.cezarykluczynski.stapi.server.company.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper;
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanyRestReader implements BaseReader<CompanyRestBeanParams, CompanyBaseResponse>, FullReader<String, CompanyFullResponse> {

	private final CompanyRestQuery companyRestQuery;

	private final CompanyBaseRestMapper companyBaseRestMapper;

	private final CompanyFullRestMapper companyFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public CompanyRestReader(CompanyRestQuery companyRestQuery, CompanyBaseRestMapper companyBaseRestMapper,
			CompanyFullRestMapper companyFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.companyRestQuery = companyRestQuery;
		this.companyBaseRestMapper = companyBaseRestMapper;
		this.companyFullRestMapper = companyFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public CompanyBaseResponse readBase(CompanyRestBeanParams input) {
		Page<Company> companyPage = companyRestQuery.query(input);
		CompanyBaseResponse companyResponse = new CompanyBaseResponse();
		companyResponse.setPage(pageMapper.fromPageToRestResponsePage(companyPage));
		companyResponse.setSort(sortMapper.map(input.getSort()));
		companyResponse.getCompanies().addAll(companyBaseRestMapper.mapBase(companyPage.getContent()));
		return companyResponse;
	}

	@Override
	public CompanyFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams();
		companyRestBeanParams.setUid(uid);
		Page<Company> companyPage = companyRestQuery.query(companyRestBeanParams);
		CompanyFullResponse companyResponse = new CompanyFullResponse();
		companyResponse.setCompany(companyFullRestMapper.mapFull(Iterables.getOnlyElement(companyPage.getContent(), null)));
		return companyResponse;
	}
}
