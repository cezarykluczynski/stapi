package com.cezarykluczynski.stapi.model.spacecraft.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft_
import com.cezarykluczynski.stapi.model.spacecraft.query.SpacecraftQueryBuilderFactory
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SpacecraftRepositoryImplTest extends AbstractSpacecraftTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SpacecraftQueryBuilderFactory spacecraftQueryBuilderFactoryMock

	private SpacecraftRepositoryImpl spacecraftRepositoryImpl

	private QueryBuilder<Spacecraft> spacecraftQueryBuilder

	private Pageable pageable

	private SpacecraftRequestDTO spacecraftRequestDTO

	private Page page

	void setup() {
		spacecraftQueryBuilderFactoryMock = Mock()
		spacecraftRepositoryImpl = new SpacecraftRepositoryImpl(spacecraftQueryBuilderFactoryMock)
		spacecraftQueryBuilder = Mock()
		pageable = Mock()
		spacecraftRequestDTO = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = spacecraftRepositoryImpl.findMatching(spacecraftRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * spacecraftQueryBuilderFactoryMock.createQueryBuilder(pageable) >> spacecraftQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * spacecraftRequestDTO.uid >> UID
		1 * spacecraftQueryBuilder.equal(Spacecraft_.uid, UID)

		then: 'string criteria are set'
		1 * spacecraftRequestDTO.name >> NAME
		1 * spacecraftQueryBuilder.like(Spacecraft_.name, NAME)

		then: 'sort is set'
		1 * spacecraftRequestDTO.sort >> SORT
		1 * spacecraftQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.species, true)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.owner, true)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.operator, true)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftClass, SpacecraftClass_.affiliation, true)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.owner)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.operator)
		1 * spacecraftQueryBuilder.fetch(Spacecraft_.spacecraftTypes, true)

		then: 'page is retrieved'
		1 * spacecraftQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
