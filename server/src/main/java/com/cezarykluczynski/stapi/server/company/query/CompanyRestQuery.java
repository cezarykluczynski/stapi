package com.cezarykluczynski.stapi.server.company.query;

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CompanyRestQuery {

	private CompanyRestMapper companyRestMapper;

	private PageMapper pageMapper;

	private CompanyRepository companyRepository;

	@Inject
	public CompanyRestQuery(CompanyRestMapper companyRestMapper, PageMapper pageMapper,
			CompanyRepository companyRepository) {
		this.companyRestMapper = companyRestMapper;
		this.pageMapper = pageMapper;
		this.companyRepository = companyRepository;
	}

	public Page<Company> query(CompanyRestBeanParams companyRestBeanParams) {
		CompanyRequestDTO companyRequestDTO = companyRestMapper.mapBase(companyRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(companyRestBeanParams);
		return companyRepository.findMatching(companyRequestDTO, pageRequest);
	}

}
