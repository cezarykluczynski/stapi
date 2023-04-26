package com.cezarykluczynski.stapi.model.title.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.entity.Title_;
import com.cezarykluczynski.stapi.model.title.query.TitleQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TitleRepositoryImpl implements TitleRepositoryCustom {

	private final TitleQueryBuilderFactory titleQueryBuilderFactory;

	public TitleRepositoryImpl(TitleQueryBuilderFactory titleQueryBuilderFactory) {
		this.titleQueryBuilderFactory = titleQueryBuilderFactory;
	}

	@Override
	public Page<Title> findMatching(TitleRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Title> titleQueryBuilder = titleQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		titleQueryBuilder.equal(Title_.uid, uid);
		titleQueryBuilder.like(Title_.name, criteria.getName());
		titleQueryBuilder.equal(Title_.militaryRank, criteria.getMilitaryRank());
		titleQueryBuilder.equal(Title_.fleetRank, criteria.getFleetRank());
		titleQueryBuilder.equal(Title_.religiousTitle, criteria.getReligiousTitle());
		titleQueryBuilder.equal(Title_.educationTitle, criteria.getEducationTitle());
		titleQueryBuilder.equal(Title_.position, criteria.getPosition());
		titleQueryBuilder.equal(Title_.mirror, criteria.getMirror());
		titleQueryBuilder.setSort(criteria.getSort());
		titleQueryBuilder.fetch(Title_.characters, doFetch);

		return titleQueryBuilder.findPage();
	}

}
