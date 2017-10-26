package com.cezarykluczynski.stapi.model.comic_strip.query;

import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ComicStripQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicStrip> {

	public ComicStripQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, ComicStrip.class);
	}

}
