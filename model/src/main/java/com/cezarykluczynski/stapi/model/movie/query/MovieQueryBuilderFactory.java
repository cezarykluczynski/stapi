package com.cezarykluczynski.stapi.model.movie.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.CachingStrategy;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MovieQueryBuilderFactory extends AbstractQueryBuilderFactory<Movie> {

	@Inject
	public MovieQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Movie.class);
	}

}
