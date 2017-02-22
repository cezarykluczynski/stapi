package com.cezarykluczynski.stapi.server.company.query;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CompanySoapQuery {

	private CompanySoapMapper companySoapMapper;

	private PageMapper pageMapper;

	private CompanyRepository companyRepository;

	@Inject
	public CompanySoapQuery(CompanySoapMapper companySoapMapper, PageMapper pageMapper, CompanyRepository companyRepository) {
		this.companySoapMapper = companySoapMapper;
		this.pageMapper = pageMapper;
		this.companyRepository = companyRepository;
	}

	public Page<Company> query(CompanyRequest companyRequest) {
		CompanyRequestDTO companyRequestDTO = companySoapMapper.map(companyRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(companyRequest.getPage());
		return companyRepository.findMatching(companyRequestDTO, pageRequest);
	}

}
