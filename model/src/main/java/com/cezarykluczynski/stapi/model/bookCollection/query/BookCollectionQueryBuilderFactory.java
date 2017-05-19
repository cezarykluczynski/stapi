package com.cezarykluczynski.stapi.model.bookCollection.query;

import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookCollectionQueryBuilderFactory extends AbstractQueryBuilderFactory<BookCollection> {

	@Inject
	public BookCollectionQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, BookCollection.class);
	}

}
