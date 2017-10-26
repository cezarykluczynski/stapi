package com.cezarykluczynski.stapi.model.location.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.location.entity.Location_;
import com.cezarykluczynski.stapi.model.location.query.LocationQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryImpl implements LocationRepositoryCustom {

	private final LocationQueryBuilderFactory locationQueryBuilderFactory;

	public LocationRepositoryImpl(LocationQueryBuilderFactory locationQueryBuilderFactory) {
		this.locationQueryBuilderFactory = locationQueryBuilderFactory;
	}

	@Override
	public Page<Location> findMatching(LocationRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Location> locationQueryBuilder = locationQueryBuilderFactory.createQueryBuilder(pageable);

		locationQueryBuilder.equal(Location_.uid, criteria.getUid());
		locationQueryBuilder.like(Location_.name, criteria.getName());
		locationQueryBuilder.equal(Location_.earthlyLocation, criteria.getEarthlyLocation());
		locationQueryBuilder.equal(Location_.fictionalLocation, criteria.getFictionalLocation());
		locationQueryBuilder.equal(Location_.religiousLocation, criteria.getReligiousLocation());
		locationQueryBuilder.equal(Location_.geographicalLocation, criteria.getGeographicalLocation());
		locationQueryBuilder.equal(Location_.bodyOfWater, criteria.getBodyOfWater());
		locationQueryBuilder.equal(Location_.country, criteria.getCountry());
		locationQueryBuilder.equal(Location_.subnationalEntity, criteria.getSubnationalEntity());
		locationQueryBuilder.equal(Location_.settlement, criteria.getSettlement());
		locationQueryBuilder.equal(Location_.usSettlement, criteria.getUsSettlement());
		locationQueryBuilder.equal(Location_.bajoranSettlement, criteria.getBajoranSettlement());
		locationQueryBuilder.equal(Location_.colony, criteria.getColony());
		locationQueryBuilder.equal(Location_.landform, criteria.getLandform());
		locationQueryBuilder.equal(Location_.landmark, criteria.getLandmark());
		locationQueryBuilder.equal(Location_.road, criteria.getRoad());
		locationQueryBuilder.equal(Location_.structure, criteria.getStructure());
		locationQueryBuilder.equal(Location_.shipyard, criteria.getShipyard());
		locationQueryBuilder.equal(Location_.buildingInterior, criteria.getBuildingInterior());
		locationQueryBuilder.equal(Location_.establishment, criteria.getEstablishment());
		locationQueryBuilder.equal(Location_.medicalEstablishment, criteria.getMedicalEstablishment());
		locationQueryBuilder.equal(Location_.ds9Establishment, criteria.getDs9Establishment());
		locationQueryBuilder.equal(Location_.school, criteria.getSchool());
		locationQueryBuilder.equal(Location_.mirror, criteria.getMirror());
		locationQueryBuilder.equal(Location_.alternateReality, criteria.getAlternateReality());
		locationQueryBuilder.setSort(criteria.getSort());

		return locationQueryBuilder.findPage();
	}

}
