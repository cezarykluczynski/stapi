package com.cezarykluczynski.stapi.model.location.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LocationQueryBuilderFactory extends AbstractQueryBuilderFactory<Location> {

	@Inject
	public LocationQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Location.class);
	}

}
