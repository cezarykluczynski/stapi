package com.cezarykluczynski.stapi.model.organization.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class OrganizationQueryBuilderFactory extends AbstractQueryBuilderFactory<Organization> {

	@Inject
	public OrganizationQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Organization.class);
	}

}
