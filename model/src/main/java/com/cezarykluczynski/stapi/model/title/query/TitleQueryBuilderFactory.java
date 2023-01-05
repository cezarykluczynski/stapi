package com.cezarykluczynski.stapi.model.title.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class TitleQueryBuilderFactory extends AbstractQueryBuilderFactory<Title> {

	public TitleQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Title.class);
	}

}
