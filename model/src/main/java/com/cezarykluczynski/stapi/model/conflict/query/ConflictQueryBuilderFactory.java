package com.cezarykluczynski.stapi.model.conflict.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ConflictQueryBuilderFactory extends AbstractQueryBuilderFactory<Conflict> {

	public ConflictQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Conflict.class);
	}

}
