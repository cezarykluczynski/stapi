package com.cezarykluczynski.stapi.model.book_collection.query;

import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionQueryBuilderFactory extends AbstractQueryBuilderFactory<BookCollection> {

	public BookCollectionQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, BookCollection.class);
	}

}
