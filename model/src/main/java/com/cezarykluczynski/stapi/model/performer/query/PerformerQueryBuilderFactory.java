package com.cezarykluczynski.stapi.model.performer.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerQueryBuilderFactory extends AbstractQueryBuilderFactory<Performer> {

	@Inject
	public PerformerQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Performer.class);
	}

}
