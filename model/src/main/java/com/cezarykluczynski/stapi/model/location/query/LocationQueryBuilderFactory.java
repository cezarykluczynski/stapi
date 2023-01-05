package com.cezarykluczynski.stapi.model.location.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class LocationQueryBuilderFactory extends AbstractQueryBuilderFactory<Location> {

	public LocationQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Location.class);
	}

}
