package com.cezarykluczynski.stapi.server.company.reader;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
import com.cezarykluczynski.stapi.server.company.query.CompanySoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanySoapReader implements BaseReader<CompanyBaseRequest, CompanyBaseResponse>, FullReader<CompanyFullRequest, CompanyFullResponse> {

	private CompanySoapQuery companySoapQuery;

	private CompanySoapMapper companySoapMapper;

	private PageMapper pageMapper;

	public CompanySoapReader(CompanySoapQuery companySoapQuery, CompanySoapMapper companySoapMapper, PageMapper pageMapper) {
		this.companySoapQuery = companySoapQuery;
		this.companySoapMapper = companySoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CompanyBaseResponse readBase(CompanyBaseRequest input) {
		Page<Company> companyPage = companySoapQuery.query(input);
		CompanyBaseResponse companyResponse = new CompanyBaseResponse();
		companyResponse.setPage(pageMapper.fromPageToSoapResponsePage(companyPage));
		companyResponse.getCompanies().addAll(companySoapMapper.mapBase(companyPage.getContent()));
		return companyResponse;
	}

	@Override
	public CompanyFullResponse readFull(CompanyFullRequest input) {
		Page<Company> companyPage = companySoapQuery.query(input);
		CompanyFullResponse companyFullResponse = new CompanyFullResponse();
		companyFullResponse.setCompany(companySoapMapper.mapFull(Iterables.getOnlyElement(companyPage.getContent(), null)));
		return companyFullResponse;
	}
}
