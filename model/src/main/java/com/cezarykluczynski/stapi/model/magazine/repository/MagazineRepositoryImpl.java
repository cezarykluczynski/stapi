package com.cezarykluczynski.stapi.model.magazine.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine_;
import com.cezarykluczynski.stapi.model.magazine.query.MagazineQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MagazineRepositoryImpl implements MagazineRepositoryCustom {

	private final MagazineQueryBuilderFactory magazineQueryBuilderFactory;

	public MagazineRepositoryImpl(MagazineQueryBuilderFactory magazineQueryBuilderFactory) {
		this.magazineQueryBuilderFactory = magazineQueryBuilderFactory;
	}

	@Override
	public Page<Magazine> findMatching(MagazineRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Magazine> magazineQueryBuilder = magazineQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		magazineQueryBuilder.equal(Magazine_.uid, uid);
		magazineQueryBuilder.like(Magazine_.title, criteria.getTitle());
		magazineQueryBuilder.between(Magazine_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		magazineQueryBuilder.between(Magazine_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		magazineQueryBuilder.setSort(criteria.getSort());
		magazineQueryBuilder.fetch(Magazine_.magazineSeries, doFetch);
		magazineQueryBuilder.fetch(Magazine_.editors, doFetch);
		magazineQueryBuilder.fetch(Magazine_.publishers, doFetch);

		return magazineQueryBuilder.findPage();
	}

}
