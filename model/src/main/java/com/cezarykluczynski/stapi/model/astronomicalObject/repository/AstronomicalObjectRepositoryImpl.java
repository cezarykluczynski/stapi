package com.cezarykluczynski.stapi.model.astronomicalObject.repository;


import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject_;
import com.cezarykluczynski.stapi.model.astronomicalObject.query.AstronomicalObjectQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class AstronomicalObjectRepositoryImpl implements AstronomicalObjectRepositoryCustom {

	private AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory;

	@Inject
	public AstronomicalObjectRepositoryImpl(AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory) {
		this.astronomicalObjectQueryBuilderFactory = astronomicalObjectQueryBuilderFactory;
	}

	@Override
	public Page<AstronomicalObject> findMatching(AstronomicalObjectRequestDTO criteria, Pageable pageable) {
		QueryBuilder<AstronomicalObject> astronomicalObjectQueryBuilder = astronomicalObjectQueryBuilderFactory.createQueryBuilder(pageable);

		astronomicalObjectQueryBuilder.equal(AstronomicalObject_.guid, criteria.getGuid());
		astronomicalObjectQueryBuilder.like(AstronomicalObject_.name, criteria.getName());
		astronomicalObjectQueryBuilder.joinPropertyEqual(AstronomicalObject_.location, "guid", criteria.getLocationGuid());
		astronomicalObjectQueryBuilder.equal(AstronomicalObject_.astronomicalObjectType, criteria.getAstronomicalObjectType());
		astronomicalObjectQueryBuilder.setSort(criteria.getSort());
		astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.location);

		return astronomicalObjectQueryBuilder.findPage();
	}

}
