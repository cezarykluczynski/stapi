package com.cezarykluczynski.stapi.model.occupation.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class OccupationQueryBuilderFactory extends AbstractQueryBuilderFactory<Occupation> {

	public OccupationQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Occupation.class);
	}

}
