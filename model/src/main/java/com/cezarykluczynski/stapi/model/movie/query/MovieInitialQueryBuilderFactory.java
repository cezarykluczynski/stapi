package com.cezarykluczynski.stapi.model.movie.query;

import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.entity.Movie_;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieInitialQueryBuilderFactory implements InitialQueryBuilderFactory<MovieRequestDTO, Movie> {

	private final MovieQueryBuilderFactory movieQueryBuilderFactory;

	public MovieInitialQueryBuilderFactory(MovieQueryBuilderFactory movieQueryBuilderFactory) {
		this.movieQueryBuilderFactory = movieQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<Movie> createInitialQueryBuilder(MovieRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Movie> movieQueryBuilder = movieQueryBuilderFactory.createQueryBuilder(pageable);

		movieQueryBuilder.equal(Movie_.uid, criteria.getUid());
		movieQueryBuilder.like(Movie_.title, criteria.getTitle());
		movieQueryBuilder.between(Movie_.yearFrom, criteria.getYearFrom(), null);
		movieQueryBuilder.between(Movie_.yearTo, null, criteria.getYearTo());
		movieQueryBuilder.between(Movie_.stardateFrom, criteria.getStardateFrom(), null);
		movieQueryBuilder.between(Movie_.stardateTo, null, criteria.getStardateTo());
		movieQueryBuilder.between(Movie_.usReleaseDate, criteria.getUsReleaseDateFrom(), criteria.getUsReleaseDateTo());
		movieQueryBuilder.setSort(criteria.getSort());

		return movieQueryBuilder;
	}
}
