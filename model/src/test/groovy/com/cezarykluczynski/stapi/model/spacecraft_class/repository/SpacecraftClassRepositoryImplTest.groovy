package com.cezarykluczynski.stapi.model.spacecraft_class.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_
import com.cezarykluczynski.stapi.model.spacecraft_class.query.SpacecraftClassQueryBuilderFactory
import com.cezarykluczynski.stapi.model.species.entity.Species_
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SpacecraftClassRepositoryImplTest extends AbstractSpacecraftClassTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactoryMock

	private SpacecraftClassRepositoryImpl spacecraftClassRepositoryImpl

	private QueryBuilder<SpacecraftClass> spacecraftClassQueryBuilder

	private Pageable pageable

	private SpacecraftClassRequestDTO spacecraftClassRequestDTO

	private SpacecraftClass spacecraftClass

	private Page page

	void setup() {
		spacecraftClassQueryBuilderFactoryMock = Mock()
		spacecraftClassRepositoryImpl = new SpacecraftClassRepositoryImpl(spacecraftClassQueryBuilderFactoryMock)
		spacecraftClassQueryBuilder = Mock()
		pageable = Mock()
		spacecraftClassRequestDTO = Mock()
		page = Mock()
		spacecraftClass = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = spacecraftClassRepositoryImpl.findMatching(spacecraftClassRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * spacecraftClassQueryBuilderFactoryMock.createQueryBuilder(pageable) >> spacecraftClassQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * spacecraftClassRequestDTO.uid >> UID
		1 * spacecraftClassQueryBuilder.equal(SpacecraftClass_.uid, UID)

		then: 'string criteria are set'
		1 * spacecraftClassRequestDTO.name >> NAME
		1 * spacecraftClassQueryBuilder.like(SpacecraftClass_.name, NAME)

		then: 'boolean criteria are set'
		1 * spacecraftClassRequestDTO.warpCapable >> WARP_CAPABLE_SPECIES
		1 * spacecraftClassQueryBuilder.equal(SpacecraftClass_.warpCapable, WARP_CAPABLE_SPECIES)
		1 * spacecraftClassRequestDTO.mirror >> MIRROR
		1 * spacecraftClassQueryBuilder.equal(SpacecraftClass_.mirror, MIRROR)
		1 * spacecraftClassRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * spacecraftClassQueryBuilder.equal(Species_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * spacecraftClassRequestDTO.sort >> SORT
		1 * spacecraftClassQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.owners, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.operators, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.affiliations, true)
		1 * spacecraftClassQueryBuilder.divideQueries()
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecraftTypes, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.armaments, true)
		1 * spacecraftClassQueryBuilder.divideQueries()
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.defenses, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, true)

		then: 'page is retrieved'
		1 * spacecraftClassQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
