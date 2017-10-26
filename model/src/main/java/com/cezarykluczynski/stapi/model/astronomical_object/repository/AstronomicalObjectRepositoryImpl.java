package com.cezarykluczynski.stapi.model.astronomical_object.repository;


import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject_;
import com.cezarykluczynski.stapi.model.astronomical_object.query.AstronomicalObjectQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class AstronomicalObjectRepositoryImpl implements AstronomicalObjectRepositoryCustom {

	private final AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory;

	public AstronomicalObjectRepositoryImpl(AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderFactory) {
		this.astronomicalObjectQueryBuilderFactory = astronomicalObjectQueryBuilderFactory;
	}

	@Override
	public Page<AstronomicalObject> findMatching(AstronomicalObjectRequestDTO criteria, Pageable pageable) {
		QueryBuilder<AstronomicalObject> astronomicalObjectQueryBuilder = astronomicalObjectQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		astronomicalObjectQueryBuilder.equal(AstronomicalObject_.uid, uid);
		astronomicalObjectQueryBuilder.like(AstronomicalObject_.name, criteria.getName());
		astronomicalObjectQueryBuilder.joinPropertyEqual(AstronomicalObject_.location, "uid", criteria.getLocationUid());
		astronomicalObjectQueryBuilder.equal(AstronomicalObject_.astronomicalObjectType, criteria.getAstronomicalObjectType());
		astronomicalObjectQueryBuilder.setSort(criteria.getSort());
		astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.location);
		astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.location, AstronomicalObject_.location, doFetch);
		astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.astronomicalObjects, AstronomicalObject_.location, doFetch);

		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectQueryBuilder.findPage();
		addLocationToChildren(astronomicalObjectPage, doFetch);
		return astronomicalObjectPage;
	}

	private void addLocationToChildren(Page<AstronomicalObject> astronomicalObjectPage, boolean doFetch) {
		if (astronomicalObjectPage.getTotalElements() != 1 || !doFetch) {
			return;
		}

		AstronomicalObject parentAstronomicalObject = astronomicalObjectPage.getContent().get(0);

		parentAstronomicalObject.getAstronomicalObjects()
				.forEach(childAstronomicalObject -> childAstronomicalObject.setLocation(parentAstronomicalObject));
	}

}
