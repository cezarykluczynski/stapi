package com.cezarykluczynski.stapi.model.comicStrip.query;

import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.CachingStrategy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicStrip> {

	@Inject
	public ComicStripQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, ComicStrip.class);
	}

}
