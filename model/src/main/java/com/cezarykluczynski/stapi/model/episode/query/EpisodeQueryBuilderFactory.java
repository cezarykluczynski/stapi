package com.cezarykluczynski.stapi.model.episode.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EpisodeQueryBuilderFactory extends AbstractQueryBuilderFactory<Episode> {

	@Inject
	public EpisodeQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Episode.class);
	}

}
