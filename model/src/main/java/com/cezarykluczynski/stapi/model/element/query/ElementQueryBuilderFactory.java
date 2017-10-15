package com.cezarykluczynski.stapi.model.element.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ElementQueryBuilderFactory extends AbstractQueryBuilderFactory<Element> {

	@Inject
	public ElementQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Element.class);
	}

}
