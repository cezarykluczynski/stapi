package com.cezarykluczynski.stapi.model.spacecraft.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftQueryBuilderFactory extends AbstractQueryBuilderFactory<Spacecraft> {

	public SpacecraftQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Spacecraft.class);
	}

}
