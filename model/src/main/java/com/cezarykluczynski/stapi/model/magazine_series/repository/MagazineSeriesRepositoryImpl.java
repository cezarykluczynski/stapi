package com.cezarykluczynski.stapi.model.magazine_series.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries_;
import com.cezarykluczynski.stapi.model.magazine_series.query.MagazineSeriesQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MagazineSeriesRepositoryImpl implements MagazineSeriesRepositoryCustom {

	private final MagazineSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory;

	public MagazineSeriesRepositoryImpl(MagazineSeriesQueryBuilderFactory comicSeriesQueryBuilderFactory) {
		this.comicSeriesQueryBuilderFactory = comicSeriesQueryBuilderFactory;
	}

	@Override
	public Page<MagazineSeries> findMatching(MagazineSeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<MagazineSeries> comicSeriesQueryBuilder = comicSeriesQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		comicSeriesQueryBuilder.equal(MagazineSeries_.uid, uid);
		comicSeriesQueryBuilder.like(MagazineSeries_.title, criteria.getTitle());
		comicSeriesQueryBuilder.between(MagazineSeries_.publishedYearFrom, criteria.getPublishedYearFrom(), null);
		comicSeriesQueryBuilder.between(MagazineSeries_.publishedYearTo, null, criteria.getPublishedYearTo());
		comicSeriesQueryBuilder.between(MagazineSeries_.numberOfIssues, criteria.getNumberOfIssuesFrom(), criteria.getNumberOfIssuesTo());
		comicSeriesQueryBuilder.setSort(criteria.getSort());
		comicSeriesQueryBuilder.fetch(MagazineSeries_.publishers, doFetch);
		comicSeriesQueryBuilder.fetch(MagazineSeries_.editors, doFetch);
		comicSeriesQueryBuilder.fetch(MagazineSeries_.magazines, doFetch);

		return comicSeriesQueryBuilder.findPage();
	}

}
