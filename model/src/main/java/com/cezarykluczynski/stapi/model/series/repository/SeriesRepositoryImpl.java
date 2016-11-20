package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.query.SeriesQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

	private SeriesQueryBuilderFactory seriesQueryBuilderFactory;

	@Inject
	public SeriesRepositoryImpl(SeriesQueryBuilderFactory seriesQueryBuilderFactory) {
		this.seriesQueryBuilderFactory = seriesQueryBuilderFactory;
	}

	@Override
	public Page<Series> findMatching(SeriesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Series> seriesQueryBuilder = seriesQueryBuilderFactory.createQueryBuilder(pageable);

		seriesQueryBuilder.equal("guid", criteria.getGuid());
		seriesQueryBuilder.like("title", criteria.getTitle());
		seriesQueryBuilder.like("abbreviation", criteria.getAbbreviation());
		seriesQueryBuilder.between("productionStartYear", criteria.getProductionStartYearFrom(),
				criteria.getProductionStartYearTo());
		seriesQueryBuilder.between("productionEndYear", criteria.getProductionEndYearFrom(),
				criteria.getProductionEndYearTo());
		seriesQueryBuilder.between("originalRunStartDate", criteria.getOriginalRunStartDateFrom(),
				criteria.getOriginalRunStartDateTo());
		seriesQueryBuilder.between("originalRunEndDate", criteria.getOriginalRunEndDateFrom(),
				criteria.getOriginalRunEndDateTo());

		return seriesQueryBuilder.findPage();
	}

}
