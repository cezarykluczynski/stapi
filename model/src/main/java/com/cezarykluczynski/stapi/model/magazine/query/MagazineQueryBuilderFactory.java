package com.cezarykluczynski.stapi.model.magazine.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class MagazineQueryBuilderFactory extends AbstractQueryBuilderFactory<Magazine> {

	public MagazineQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Magazine.class);
	}

}
