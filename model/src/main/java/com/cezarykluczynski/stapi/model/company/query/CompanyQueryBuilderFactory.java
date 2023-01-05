package com.cezarykluczynski.stapi.model.company.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class CompanyQueryBuilderFactory extends AbstractQueryBuilderFactory<Company> {

	public CompanyQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Company.class);
	}

}
