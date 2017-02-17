package com.cezarykluczynski.stapi.server.company.reader;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyResponse;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
import com.cezarykluczynski.stapi.server.company.query.CompanySoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanySoapReader implements Reader<CompanyRequest, CompanyResponse> {

	private CompanySoapQuery companySoapQuery;

	private CompanySoapMapper companySoapMapper;

	private PageMapper pageMapper;

	public CompanySoapReader(CompanySoapQuery companySoapQuery, CompanySoapMapper companySoapMapper, PageMapper pageMapper) {
		this.companySoapQuery = companySoapQuery;
		this.companySoapMapper = companySoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public CompanyResponse read(CompanyRequest input) {
		Page<Company> companyPage = companySoapQuery.query(input);
		CompanyResponse companyResponse = new CompanyResponse();
		companyResponse.setPage(pageMapper.fromPageToSoapResponsePage(companyPage));
		companyResponse.getCompanies().addAll(companySoapMapper.map(companyPage.getContent()));
		return companyResponse;
	}

}
