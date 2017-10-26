package com.cezarykluczynski.stapi.model.astronomical_object.query;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectQueryBuilderFactory extends AbstractQueryBuilderFactory<AstronomicalObject> {

	public AstronomicalObjectQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, AstronomicalObject.class);
	}

}
