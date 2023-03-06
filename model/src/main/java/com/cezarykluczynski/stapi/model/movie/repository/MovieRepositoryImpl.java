package com.cezarykluczynski.stapi.model.movie.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.entity.Movie_;
import com.cezarykluczynski.stapi.model.movie.query.MovieQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepositoryImpl extends AbstractRepositoryImpl<Movie> implements MovieRepositoryCustom {

	private final MovieQueryBuilderFactory movieQueryBuilderFactory;

	public MovieRepositoryImpl(MovieQueryBuilderFactory movieQueryBuilderFactory) {
		this.movieQueryBuilderFactory = movieQueryBuilderFactory;
	}

	@Override
	public Page<Movie> findMatching(MovieRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Movie> movieQueryBuilder = movieQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		movieQueryBuilder.equal(Movie_.uid, uid);
		movieQueryBuilder.like(Movie_.title, criteria.getTitle());
		movieQueryBuilder.between(Movie_.yearFrom, criteria.getYearFrom(), null);
		movieQueryBuilder.between(Movie_.yearTo, null, criteria.getYearTo());
		movieQueryBuilder.between(Movie_.stardateFrom, criteria.getStardateFrom(), null);
		movieQueryBuilder.between(Movie_.stardateTo, null, criteria.getStardateTo());
		movieQueryBuilder.between(Movie_.usReleaseDate, criteria.getUsReleaseDateFrom(), criteria.getUsReleaseDateTo());
		movieQueryBuilder.setSort(criteria.getSort());
		movieQueryBuilder.fetch(Movie_.mainDirector);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.writers, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.screenplayAuthors, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.storyAuthors, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.directors, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.producers, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.staff, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.performers, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.stuntPerformers, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.standInPerformers, doFetch);
		movieQueryBuilder.divideQueries();
		movieQueryBuilder.fetch(Movie_.characters, doFetch);

		Page<Movie> moviePage = movieQueryBuilder.findPage();
		clearProxies(moviePage, !doFetch);
		return moviePage;
	}

	@Override
	protected void clearProxies(Page<Movie> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(movie -> {
			movie.setWriters(Sets.newHashSet());
			movie.setScreenplayAuthors(Sets.newHashSet());
			movie.setStoryAuthors(Sets.newHashSet());
			movie.setDirectors(Sets.newHashSet());
			movie.setProducers(Sets.newHashSet());
			movie.setStaff(Sets.newHashSet());
			movie.setPerformers(Sets.newHashSet());
			movie.setStuntPerformers(Sets.newHashSet());
			movie.setStandInPerformers(Sets.newHashSet());
			movie.setCharacters(Sets.newHashSet());
		});
	}

}
