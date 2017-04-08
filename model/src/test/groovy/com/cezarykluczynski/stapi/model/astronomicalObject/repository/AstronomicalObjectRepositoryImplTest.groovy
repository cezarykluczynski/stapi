package com.cezarykluczynski.stapi.model.astronomicalObject.repository

import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject_
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.model.astronomicalObject.query.AstronomicalObjectQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class AstronomicalObjectRepositoryImplTest extends AbstractAstronomicalObjectTest {

	private static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectType.ASTEROID
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private AstronomicalObjectQueryBuilderFactory astronomicalObjectQueryBuilderMock

	private AstronomicalObjectRepositoryImpl astronomicalObjectRepositoryImpl

	private QueryBuilder<AstronomicalObject> astronomicalObjectQueryBuilder

	private Pageable pageable

	private AstronomicalObjectRequestDTO astronomicalObjectRequestDTO

	private AstronomicalObject astronomicalObject

	private AstronomicalObject astronomicalObjectChild

	private Page page

	void setup() {
		astronomicalObjectQueryBuilderMock = Mock()
		astronomicalObjectRepositoryImpl = new AstronomicalObjectRepositoryImpl(astronomicalObjectQueryBuilderMock)
		astronomicalObjectQueryBuilder = Mock()
		pageable = Mock()
		astronomicalObjectRequestDTO = Mock()
		page = Mock()
		astronomicalObject = Mock()
		astronomicalObjectChild = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = astronomicalObjectRepositoryImpl.findMatching(astronomicalObjectRequestDTO, pageable)

		then:
		1 * astronomicalObjectQueryBuilderMock.createQueryBuilder(pageable) >> astronomicalObjectQueryBuilder

		then: 'guid criteria is set'
		1 * astronomicalObjectRequestDTO.guid >> GUID
		1 * astronomicalObjectQueryBuilder.equal(AstronomicalObject_.guid, GUID)

		then: 'string criteria are set'
		1 * astronomicalObjectRequestDTO.name >> NAME
		1 * astronomicalObjectQueryBuilder.like(AstronomicalObject_.name, NAME)

		then: 'property join criteria are set'
		1 * astronomicalObjectRequestDTO.locationGuid >> LOCATION_GUID
		1 * astronomicalObjectQueryBuilder.joinPropertyEqual(AstronomicalObject_.location, 'guid', LOCATION_GUID)

		then: 'enum criteria is set'
		1 * astronomicalObjectRequestDTO.astronomicalObjectType >> ASTRONOMICAL_OBJECT_TYPE
		1 * astronomicalObjectQueryBuilder.equal(AstronomicalObject_.astronomicalObjectType, ASTRONOMICAL_OBJECT_TYPE)

		then: 'sort is set'
		1 * astronomicalObjectRequestDTO.sort >> SORT
		1 * astronomicalObjectQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.location)
		1 * astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.location, AstronomicalObject_.location, true)
		1 * astronomicalObjectQueryBuilder.fetch(AstronomicalObject_.astronomicalObjects, AstronomicalObject_.location, true)

		then: 'page is searched for'
		1 * astronomicalObjectQueryBuilder.findPage() >> page

		then: 'astronomical object is retrieved'
		1 * page.totalElements >> 1
		1 * page.content >> Lists.newArrayList(astronomicalObject)

		then: 'astronomical object is set as a location of child objects'
		1 * astronomicalObject.astronomicalObjects >> Sets.newHashSet(astronomicalObjectChild)
		1 * astronomicalObjectChild.setLocation(astronomicalObject)

		then: 'page is returned'
		0 * page.content
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
