package com.cezarykluczynski.stapi.model.organization.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class OrganizationQueryBuilderFactory extends AbstractQueryBuilderFactory<Organization> {

	public OrganizationQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Organization.class);
	}

}
