package com.cezarykluczynski.stapi.model.comic_collection.query;

import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicCollection> {

	public ComicCollectionQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, ComicCollection.class);
	}

}
