package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.entity.Series_;
import com.cezarykluczynski.stapi.model.series.query.SeriesQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

	private final SeriesQueryBuilderFactory seriesQueryBuilderFactory;

	public SeriesRepositoryImpl(SeriesQueryBuilderFactory seriesQueryBuilderFactory) {
		this.seriesQueryBuilderFactory = seriesQueryBuilderFactory;
	}

	@Override
	public Page<Series> findMatching(SeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Series> seriesQueryBuilder = seriesQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		seriesQueryBuilder.equal(Series_.uid, uid);
		seriesQueryBuilder.like(Series_.title, criteria.getTitle());
		seriesQueryBuilder.like(Series_.abbreviation, criteria.getAbbreviation());
		seriesQueryBuilder.between(Series_.productionStartYear, criteria.getProductionStartYearFrom(), criteria.getProductionStartYearTo());
		seriesQueryBuilder.between(Series_.productionEndYear, criteria.getProductionEndYearFrom(), criteria.getProductionEndYearTo());
		seriesQueryBuilder.between(Series_.originalRunStartDate, criteria.getOriginalRunStartDateFrom(), criteria.getOriginalRunStartDateTo());
		seriesQueryBuilder.between(Series_.originalRunEndDate, criteria.getOriginalRunEndDateFrom(), criteria.getOriginalRunEndDateTo());
		seriesQueryBuilder.equal(Series_.companionSeries, criteria.getCompanionSeries());
		seriesQueryBuilder.setSort(criteria.getSort());
		seriesQueryBuilder.fetch(Series_.productionCompany);
		seriesQueryBuilder.fetch(Series_.originalBroadcaster);
		seriesQueryBuilder.fetch(Series_.seasons, doFetch);
		seriesQueryBuilder.divideQueries();
		seriesQueryBuilder.fetch(Series_.episodes, doFetch);

		Page<Series> seriesPage = seriesQueryBuilder.findPage();
		clearProxies(seriesPage, !doFetch);
		return seriesPage;
	}

	private void clearProxies(Page<Series> page, boolean doClearProxies) {
		if (!doClearProxies) {
			page.getContent().forEach(series -> {
				series.getEpisodes().forEach(episode -> {
					episode.setSeries(series);
				});
			});
		}
	}

}
