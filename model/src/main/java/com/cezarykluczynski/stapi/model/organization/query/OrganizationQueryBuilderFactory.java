package com.cezarykluczynski.stapi.model.organization.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class OrganizationQueryBuilderFactory extends AbstractQueryBuilderFactory<Organization> {

	public OrganizationQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Organization.class);
	}

}
