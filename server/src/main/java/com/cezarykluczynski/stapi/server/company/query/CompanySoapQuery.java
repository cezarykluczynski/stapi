package com.cezarykluczynski.stapi.server.company.query;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanySoapQuery {

	private final CompanyBaseSoapMapper companyBaseSoapMapper;

	private final CompanyFullSoapMapper companyFullSoapMapper;

	private final PageMapper pageMapper;

	private final CompanyRepository companyRepository;

	public CompanySoapQuery(CompanyBaseSoapMapper companyBaseSoapMapper, CompanyFullSoapMapper companyFullSoapMapper, PageMapper pageMapper,
			CompanyRepository companyRepository) {
		this.companyBaseSoapMapper = companyBaseSoapMapper;
		this.companyFullSoapMapper = companyFullSoapMapper;
		this.pageMapper = pageMapper;
		this.companyRepository = companyRepository;
	}

	public Page<Company> query(CompanyBaseRequest companyBaseRequest) {
		CompanyRequestDTO companyRequestDTO = companyBaseSoapMapper.mapBase(companyBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(companyBaseRequest.getPage());
		return companyRepository.findMatching(companyRequestDTO, pageRequest);
	}

	public Page<Company> query(CompanyFullRequest companyFullRequest) {
		CompanyRequestDTO seriesRequestDTO = companyFullSoapMapper.mapFull(companyFullRequest);
		return companyRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
