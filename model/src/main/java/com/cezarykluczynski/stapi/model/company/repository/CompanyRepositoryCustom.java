package com.cezarykluczynski.stapi.model.company.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;

public interface CompanyRepositoryCustom extends CriteriaMatcher<CompanyRequestDTO, Company> {
}
