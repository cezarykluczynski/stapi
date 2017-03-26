package com.cezarykluczynski.stapi.server.company.query;

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CompanyRestQuery {

	private CompanyBaseRestMapper companyBaseRestMapper;

	private PageMapper pageMapper;

	private CompanyRepository companyRepository;

	@Inject
	public CompanyRestQuery(CompanyBaseRestMapper companyBaseRestMapper, PageMapper pageMapper, CompanyRepository companyRepository) {
		this.companyBaseRestMapper = companyBaseRestMapper;
		this.pageMapper = pageMapper;
		this.companyRepository = companyRepository;
	}

	public Page<Company> query(CompanyRestBeanParams companyRestBeanParams) {
		CompanyRequestDTO companyRequestDTO = companyBaseRestMapper.mapBase(companyRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(companyRestBeanParams);
		return companyRepository.findMatching(companyRequestDTO, pageRequest);
	}

}
