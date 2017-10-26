package com.cezarykluczynski.stapi.model.episode.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class EpisodeQueryBuilderFactory extends AbstractQueryBuilderFactory<Episode> {

	public EpisodeQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Episode.class);
	}

}
