package com.cezarykluczynski.stapi.model.comics.query;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.entity.Comics_;
import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComicsInitialQueryBuilderFactory implements InitialQueryBuilderFactory<ComicsRequestDTO, Comics> {

	private final ComicsQueryBuilderFactory comicsQueryBuilderFactory;

	public ComicsInitialQueryBuilderFactory(ComicsQueryBuilderFactory comicsQueryBuilderFactory) {
		this.comicsQueryBuilderFactory = comicsQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<Comics> createInitialQueryBuilder(ComicsRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Comics> comicsQueryBuilder = comicsQueryBuilderFactory.createQueryBuilder(pageable);

		comicsQueryBuilder.equal(Comics_.uid, criteria.getUid());
		comicsQueryBuilder.like(Comics_.title, criteria.getTitle());
		comicsQueryBuilder.between(Comics_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		comicsQueryBuilder.between(Comics_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicsQueryBuilder.between(Comics_.yearFrom, criteria.getYearFrom(), null);
		comicsQueryBuilder.between(Comics_.yearTo, null, criteria.getYearTo());
		comicsQueryBuilder.between(Comics_.stardateFrom, criteria.getStardateFrom(), null);
		comicsQueryBuilder.between(Comics_.stardateTo, null, criteria.getStardateTo());
		comicsQueryBuilder.equal(Comics_.photonovel, criteria.getPhotonovel());
		comicsQueryBuilder.equal(Comics_.adaptation, criteria.getAdaptation());

		comicsQueryBuilder.setSort(criteria.getSort());

		return comicsQueryBuilder;
	}
}
