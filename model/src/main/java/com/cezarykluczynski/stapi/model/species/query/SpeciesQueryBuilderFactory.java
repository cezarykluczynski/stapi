package com.cezarykluczynski.stapi.model.species.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SpeciesQueryBuilderFactory extends AbstractQueryBuilderFactory<Species> {

	public SpeciesQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Species.class);
	}

}
