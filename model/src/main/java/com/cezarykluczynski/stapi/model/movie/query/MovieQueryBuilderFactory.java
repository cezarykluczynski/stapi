package com.cezarykluczynski.stapi.model.movie.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class MovieQueryBuilderFactory extends AbstractQueryBuilderFactory<Movie> {

	public MovieQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Movie.class);
	}

}
