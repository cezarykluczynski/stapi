package com.cezarykluczynski.stapi.model.comics.query;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ComicsQueryBuilderFactory extends AbstractQueryBuilderFactory<Comics> {

	public ComicsQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Comics.class);
	}

}
