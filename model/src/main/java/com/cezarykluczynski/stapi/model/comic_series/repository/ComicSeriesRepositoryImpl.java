package com.cezarykluczynski.stapi.model.comic_series.repository;

import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries_;
import com.cezarykluczynski.stapi.model.comic_series.query.ComicSeriesQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ComicSeriesRepositoryImpl extends AbstractRepositoryImpl<ComicSeries> implements ComicSeriesRepositoryCustom {

	private final ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory;

	public ComicSeriesRepositoryImpl(ComicSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory) {
		this.comicSeriesQueryBuilderFactory = comicSeriesQueryBuilderFactory;
	}

	@Override
	public Page<ComicSeries> findMatching(ComicSeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<ComicSeries> comicSeriesQueryBuilder = comicSeriesQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		comicSeriesQueryBuilder.equal(ComicSeries_.uid, uid);
		comicSeriesQueryBuilder.like(ComicSeries_.title, criteria.getTitle());
		comicSeriesQueryBuilder.between(ComicSeries_.publishedYearFrom, criteria.getPublishedYearFrom(), null);
		comicSeriesQueryBuilder.between(ComicSeries_.publishedYearTo, null, criteria.getPublishedYearTo());
		comicSeriesQueryBuilder.between(ComicSeries_.numberOfIssues, criteria.getNumberOfIssuesFrom(), criteria.getNumberOfIssuesTo());
		comicSeriesQueryBuilder.between(ComicSeries_.yearFrom, criteria.getYearFrom(), null);
		comicSeriesQueryBuilder.between(ComicSeries_.yearTo, null, criteria.getYearTo());
		comicSeriesQueryBuilder.between(ComicSeries_.stardateFrom, criteria.getStardateFrom(), null);
		comicSeriesQueryBuilder.between(ComicSeries_.stardateTo, null, criteria.getStardateTo());
		comicSeriesQueryBuilder.equal(ComicSeries_.miniseries, criteria.getMiniseries());
		comicSeriesQueryBuilder.equal(ComicSeries_.photonovelSeries, criteria.getPhotonovelSeries());
		comicSeriesQueryBuilder.setSort(criteria.getSort());
		comicSeriesQueryBuilder.fetch(ComicSeries_.parentSeries, doFetch);
		comicSeriesQueryBuilder.fetch(ComicSeries_.childSeries, doFetch);
		comicSeriesQueryBuilder.fetch(ComicSeries_.publishers, doFetch);
		comicSeriesQueryBuilder.fetch(ComicSeries_.comics, doFetch);

		Page<ComicSeries> comicSeriesPage = comicSeriesQueryBuilder.findPage();
		clearProxies(comicSeriesPage, !doFetch);
		return comicSeriesPage;
	}

	@Override
	protected void clearProxies(Page<ComicSeries> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(comicSeries -> {
			comicSeries.setParentSeries(Sets.newHashSet());
			comicSeries.setChildSeries(Sets.newHashSet());
			comicSeries.setPublishers(Sets.newHashSet());
			comicSeries.setComics(Sets.newHashSet());
		});
	}

}
