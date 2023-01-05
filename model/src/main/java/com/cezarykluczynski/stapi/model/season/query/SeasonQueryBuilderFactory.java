package com.cezarykluczynski.stapi.model.season.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SeasonQueryBuilderFactory extends AbstractQueryBuilderFactory<Season> {

	public SeasonQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Season.class);
	}

}
