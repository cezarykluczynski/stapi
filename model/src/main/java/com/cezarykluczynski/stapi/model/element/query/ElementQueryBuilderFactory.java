package com.cezarykluczynski.stapi.model.element.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ElementQueryBuilderFactory extends AbstractQueryBuilderFactory<Element> {

	public ElementQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Element.class);
	}

}
