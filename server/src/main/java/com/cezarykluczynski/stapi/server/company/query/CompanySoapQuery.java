package com.cezarykluczynski.stapi.server.company.query;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
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

	public Page<Company> query(CompanyBaseRequest companyBaseRequest) {
		CompanyRequestDTO companyRequestDTO = companySoapMapper.mapBase(companyBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(companyBaseRequest.getPage());
		return companyRepository.findMatching(companyRequestDTO, pageRequest);
	}

	public Page<Company> query(CompanyFullRequest companyFullRequest) {
		CompanyRequestDTO seriesRequestDTO = companySoapMapper.mapFull(companyFullRequest);
		return companyRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
