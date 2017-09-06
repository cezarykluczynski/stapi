package com.cezarykluczynski.stapi.model.spacecraft_class.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft_
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_
import com.cezarykluczynski.stapi.model.spacecraft_class.query.SpacecraftClassQueryBuilderFactory
import com.cezarykluczynski.stapi.model.species.entity.Species_
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
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
		1 * spacecraftClassRequestDTO.warpCapable >> WARP_CAPABLE
		1 * spacecraftClassQueryBuilder.equal(Species_.warpCapableSpecies, WARP_CAPABLE)
		1 * spacecraftClassRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * spacecraftClassQueryBuilder.equal(Species_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * spacecraftClassRequestDTO.sort >> SORT
		1 * spacecraftClassQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species, Species_.homeworld, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species, Species_.quadrant, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.owner)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.operator)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.affiliation)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecraftTypes, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, Spacecraft_.owner, true)
		1 * spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, Spacecraft_.operator, true)

		then: 'page is retrieved'
		1 * spacecraftClassQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = spacecraftClassRepositoryImpl.findMatching(spacecraftClassRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * spacecraftClassQueryBuilderFactoryMock.createQueryBuilder(pageable) >> spacecraftClassQueryBuilder

		then: 'uid criteria is set to null'
		1 * spacecraftClassRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * spacecraftClassQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(spacecraftClass)
		1 * spacecraftClass.setSpacecraftTypes(Sets.newHashSet())
		1 * spacecraftClass.setSpacecrafts(Sets.newHashSet())
		pageOutput == page
	}

}
