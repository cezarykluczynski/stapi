package com.cezarykluczynski.stapi.model.company.repository;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {

	Optional<Company> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

}
