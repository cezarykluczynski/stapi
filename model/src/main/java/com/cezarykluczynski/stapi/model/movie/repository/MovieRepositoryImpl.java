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

import javax.inject.Inject;

@Repository
public class MovieRepositoryImpl extends AbstractRepositoryImpl<Movie> implements MovieRepositoryCustom {

	private MovieQueryBuilderFactory movieQueryBuilderFactory;

	@Inject
	public MovieRepositoryImpl(MovieQueryBuilderFactory movieQueryBuilderFactory) {
		this.movieQueryBuilderFactory = movieQueryBuilderFactory;
	}

	@Override
	public Page<Movie> findMatching(MovieRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Movie> movieQueryBuilder = movieQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		movieQueryBuilder.equal(Movie_.guid, guid);
		movieQueryBuilder.like(Movie_.title, criteria.getTitle());
		movieQueryBuilder.between(Movie_.yearFrom, criteria.getYearFrom(), null);
		movieQueryBuilder.between(Movie_.yearTo, null, criteria.getYearTo());
		movieQueryBuilder.between(Movie_.stardateFrom, criteria.getStardateFrom(), null);
		movieQueryBuilder.between(Movie_.stardateTo, null, criteria.getStardateTo());
		movieQueryBuilder.between(Movie_.usReleaseDate, criteria.getUsReleaseDateFrom(), criteria.getUsReleaseDateTo());
		movieQueryBuilder.setSort(criteria.getSort());
		movieQueryBuilder.fetch(Movie_.writers, doFetch);
		movieQueryBuilder.fetch(Movie_.screenplayAuthors, doFetch);
		movieQueryBuilder.fetch(Movie_.storyAuthors, doFetch);
		movieQueryBuilder.fetch(Movie_.directors, doFetch);
		movieQueryBuilder.fetch(Movie_.producers, doFetch);
		movieQueryBuilder.fetch(Movie_.staff, doFetch);
		movieQueryBuilder.fetch(Movie_.performers, doFetch);
		movieQueryBuilder.fetch(Movie_.stuntPerformers, doFetch);
		movieQueryBuilder.fetch(Movie_.standInPerformers, doFetch);
		movieQueryBuilder.fetch(Movie_.characters, doFetch);

		Page<Movie> episodePage = movieQueryBuilder.findPage();
		clearProxies(episodePage, !doFetch);
		return episodePage;
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
