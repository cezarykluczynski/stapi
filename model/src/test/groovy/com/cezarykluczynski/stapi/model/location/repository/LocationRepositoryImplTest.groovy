package com.cezarykluczynski.stapi.model.location.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.location.entity.Location_
import com.cezarykluczynski.stapi.model.location.query.LocationQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractLocationTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class LocationRepositoryImplTest extends AbstractLocationTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private LocationQueryBuilderFactory locationQueryBuilderFactory

	private LocationRepositoryImpl locationRepositoryImpl

	private QueryBuilder<Location> locationQueryBuilder

	private Pageable pageable

	private LocationRequestDTO locationRequestDTO

	private Page page

	void setup() {
		locationQueryBuilderFactory = Mock()
		locationRepositoryImpl = new LocationRepositoryImpl(locationQueryBuilderFactory)
		locationQueryBuilder = Mock()
		pageable = Mock()
		locationRequestDTO = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = locationRepositoryImpl.findMatching(locationRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * locationQueryBuilderFactory.createQueryBuilder(pageable) >> locationQueryBuilder

		then: 'uid criteria is set'
		1 * locationRequestDTO.uid >> UID
		1 * locationQueryBuilder.equal(Location_.uid, UID)

		then: 'string criteria are set'
		1 * locationRequestDTO.name >> NAME
		1 * locationQueryBuilder.like(Location_.name, NAME)

		then: 'boolean criteria are set'
		1 * locationRequestDTO.earthlyLocation >> EARTHLY_LOCATION
		1 * locationQueryBuilder.equal(Location_.earthlyLocation, EARTHLY_LOCATION)
		1 * locationRequestDTO.fictionalLocation >> FICTIONAL_LOCATION
		1 * locationQueryBuilder.equal(Location_.fictionalLocation, FICTIONAL_LOCATION)
		1 * locationRequestDTO.religiousLocation >> RELIGIOUS_LOCATION
		1 * locationQueryBuilder.equal(Location_.religiousLocation, RELIGIOUS_LOCATION)
		1 * locationRequestDTO.geographicalLocation >> GEOGRAPHICAL_LOCATION
		1 * locationQueryBuilder.equal(Location_.geographicalLocation, GEOGRAPHICAL_LOCATION)
		1 * locationRequestDTO.bodyOfWater >> BODY_OF_WATER
		1 * locationQueryBuilder.equal(Location_.bodyOfWater, BODY_OF_WATER)
		1 * locationRequestDTO.country >> COUNTRY
		1 * locationQueryBuilder.equal(Location_.country, COUNTRY)
		1 * locationRequestDTO.subnationalEntity >> SUBNATIONAL_ENTITY
		1 * locationQueryBuilder.equal(Location_.subnationalEntity, SUBNATIONAL_ENTITY)
		1 * locationRequestDTO.settlement >> SETTLEMENT
		1 * locationQueryBuilder.equal(Location_.settlement, SETTLEMENT)
		1 * locationRequestDTO.usSettlement >> US_SETTLEMENT
		1 * locationQueryBuilder.equal(Location_.usSettlement, US_SETTLEMENT)
		1 * locationRequestDTO.bajoranSettlement >> BAJORAN_SETTLEMENT
		1 * locationQueryBuilder.equal(Location_.bajoranSettlement, BAJORAN_SETTLEMENT)
		1 * locationRequestDTO.colony >> COLONY
		1 * locationQueryBuilder.equal(Location_.colony, COLONY)
		1 * locationRequestDTO.landform >> LANDFORM
		1 * locationQueryBuilder.equal(Location_.landform, LANDFORM)
		1 * locationRequestDTO.landmark >> LANDMARK
		1 * locationQueryBuilder.equal(Location_.landmark, LANDMARK)
		1 * locationRequestDTO.road >> ROAD
		1 * locationQueryBuilder.equal(Location_.road, ROAD)
		1 * locationRequestDTO.structure >> STRUCTURE
		1 * locationQueryBuilder.equal(Location_.structure, STRUCTURE)
		1 * locationRequestDTO.shipyard >> SHIPYARD
		1 * locationQueryBuilder.equal(Location_.shipyard, SHIPYARD)
		1 * locationRequestDTO.buildingInterior >> BUILDING_INTERIOR
		1 * locationQueryBuilder.equal(Location_.buildingInterior, BUILDING_INTERIOR)
		1 * locationRequestDTO.establishment >> ESTABLISHMENT
		1 * locationQueryBuilder.equal(Location_.establishment, ESTABLISHMENT)
		1 * locationRequestDTO.medicalEstablishment >> MEDICAL_ESTABLISHMENT
		1 * locationQueryBuilder.equal(Location_.medicalEstablishment, MEDICAL_ESTABLISHMENT)
		1 * locationRequestDTO.ds9Establishment >> DS9_ESTABLISHMENT
		1 * locationQueryBuilder.equal(Location_.ds9Establishment, DS9_ESTABLISHMENT)
		1 * locationRequestDTO.school >> SCHOOL
		1 * locationQueryBuilder.equal(Location_.school, SCHOOL)
		1 * locationRequestDTO.mirror >> MIRROR
		1 * locationQueryBuilder.equal(Location_.mirror, MIRROR)
		1 * locationRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * locationQueryBuilder.equal(Location_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * locationRequestDTO.sort >> SORT
		1 * locationQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * locationQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
