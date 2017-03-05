package com.cezarykluczynski.stapi.model.company.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, PageAwareRepository<Company>, CompanyRepositoryCustom {
}
