package com.cezarykluczynski.stapi.server.company.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyResponse;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import com.cezarykluczynski.stapi.server.company.query.CompanyRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CompanyRestReader implements Reader<CompanyRestBeanParams, CompanyResponse> {

	private CompanyRestQuery companyRestQuery;

	private CompanyRestMapper companyRestMapper;

	private PageMapper pageMapper;

	@Inject
	public CompanyRestReader(CompanyRestQuery companyRestQuery, CompanyRestMapper companyRestMapper, PageMapper pageMapper) {
		this.companyRestQuery = companyRestQuery;
		this.companyRestMapper = companyRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CompanyResponse read(CompanyRestBeanParams input) {
		Page<Company> companyPage = companyRestQuery.query(input);
		CompanyResponse companyResponse = new CompanyResponse();
		companyResponse.setPage(pageMapper.fromPageToRestResponsePage(companyPage));
		companyResponse.getCompanies().addAll(companyRestMapper.map(companyPage.getContent()));
		return companyResponse;
	}

}
