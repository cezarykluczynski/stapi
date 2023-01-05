package com.cezarykluczynski.stapi.model.spacecraft.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftQueryBuilderFactory extends AbstractQueryBuilderFactory<Spacecraft> {

	public SpacecraftQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Spacecraft.class);
	}

}
