package com.cezarykluczynski.stapi.model.movie.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.entity.Movie_;
import com.cezarykluczynski.stapi.model.movie.query.MovieInitialQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class MovieRepositoryImpl extends AbstractRepositoryImpl<Movie> implements MovieRepositoryCustom {

	private MovieInitialQueryBuilderFactory movieInitialQueryBuilderFactory;

	@Inject
	public MovieRepositoryImpl(MovieInitialQueryBuilderFactory movieInitialQueryBuilderFactory) {
		this.movieInitialQueryBuilderFactory = movieInitialQueryBuilderFactory;
	}

	@Override
	public Page<Movie> findMatching(MovieRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Movie> movieQueryBuilder = createInitialMovieQueryBuilder(criteria, pageable);
		boolean doFetch = criteria.getGuid() != null;

		Page<Movie> moviePage;

		movieQueryBuilder.fetch(Movie_.mainDirector);

		if (doFetch) {
			movieQueryBuilder.fetch(Movie_.writers);
			movieQueryBuilder.fetch(Movie_.screenplayAuthors);
			movieQueryBuilder.fetch(Movie_.storyAuthors);
			movieQueryBuilder.fetch(Movie_.directors);
			movieQueryBuilder.fetch(Movie_.producers);
			movieQueryBuilder.fetch(Movie_.staff);
			moviePage = movieQueryBuilder.findPage();

			List<Movie> movieList = moviePage.getContent();

			if (movieList.size() == 0) {
				return moviePage;
			}

			Movie movie = movieList.get(0);

			QueryBuilder<Movie> moviePerformersQueryBuilder = createInitialMovieQueryBuilder(criteria, pageable);
			moviePerformersQueryBuilder.fetch(Movie_.performers);
			moviePerformersQueryBuilder.fetch(Movie_.stuntPerformers);
			moviePerformersQueryBuilder.fetch(Movie_.standInPerformers);

			List<Movie> performersMovieList = moviePerformersQueryBuilder.findAll();

			if (performersMovieList.size() == 1) {
				Movie performersMovie = performersMovieList.get(0);
				movie.setPerformers(performersMovie.getPerformers());
				movie.setStuntPerformers(performersMovie.getStuntPerformers());
				movie.setStandInPerformers(performersMovie.getStandInPerformers());
			}

			QueryBuilder<Movie> movieCharactersQueryBuilder = createInitialMovieQueryBuilder(criteria, pageable);
			movieCharactersQueryBuilder.fetch(Movie_.characters);

			List<Movie> charactersMovieList = movieCharactersQueryBuilder.findAll();

			if (charactersMovieList.size() == 1) {
				Movie charactersMovie = charactersMovieList.get(0);
				movie.setCharacters(charactersMovie.getCharacters());
			}
		} else {
			moviePage = movieQueryBuilder.findPage();
		}

		clearProxies(moviePage, !doFetch);
		return moviePage;
	}

	private QueryBuilder<Movie> createInitialMovieQueryBuilder(MovieRequestDTO criteria, Pageable pageable) {
		return movieInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

	@Override
	protected void clearProxies(Page<Movie> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(episode -> {
			episode.setWriters(Sets.newHashSet());
			episode.setScreenplayAuthors(Sets.newHashSet());
			episode.setStoryAuthors(Sets.newHashSet());
			episode.setDirectors(Sets.newHashSet());
			episode.setProducers(Sets.newHashSet());
			episode.setStaff(Sets.newHashSet());
			episode.setPerformers(Sets.newHashSet());
			episode.setStuntPerformers(Sets.newHashSet());
			episode.setStandInPerformers(Sets.newHashSet());
			episode.setCharacters(Sets.newHashSet());
		});
	}
}
