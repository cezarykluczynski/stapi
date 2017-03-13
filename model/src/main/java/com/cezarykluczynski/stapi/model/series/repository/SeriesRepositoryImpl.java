package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.entity.Series_;
import com.cezarykluczynski.stapi.model.series.query.SeriesQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class SeriesRepositoryImpl extends AbstractRepositoryImpl<Series> implements SeriesRepositoryCustom {

	private SeriesQueryBuilderFactory seriesQueryBuilderFactory;

	@Inject
	public SeriesRepositoryImpl(SeriesQueryBuilderFactory seriesQueryBuilderFactory) {
		this.seriesQueryBuilderFactory = seriesQueryBuilderFactory;
	}

	@Override
	public Page<Series> findMatching(SeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Series> seriesQueryBuilder = seriesQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		seriesQueryBuilder.equal(Series_.guid, guid);
		seriesQueryBuilder.like(Series_.title, criteria.getTitle());
		seriesQueryBuilder.like(Series_.abbreviation, criteria.getAbbreviation());
		seriesQueryBuilder.between(Series_.productionStartYear, criteria.getProductionStartYearFrom(), criteria.getProductionStartYearTo());
		seriesQueryBuilder.between(Series_.productionEndYear, criteria.getProductionEndYearFrom(), criteria.getProductionEndYearTo());
		seriesQueryBuilder.between(Series_.originalRunStartDate, criteria.getOriginalRunStartDateFrom(), criteria.getOriginalRunStartDateTo());
		seriesQueryBuilder.between(Series_.originalRunEndDate, criteria.getOriginalRunEndDateFrom(), criteria.getOriginalRunEndDateTo());
		seriesQueryBuilder.setSort(criteria.getSort());
		seriesQueryBuilder.fetch(Series_.episodes, doFetch);

		Page<Series> seriesPage = seriesQueryBuilder.findPage();
		clearProxies(seriesPage, !doFetch);
		return seriesPage;
	}

	@Override
	protected void clearProxies(Page<Series> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(series -> {
			series.setEpisodes(Sets.newHashSet());
		});
	}
}
