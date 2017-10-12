package com.cezarykluczynski.stapi.model.conflict.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict_
import com.cezarykluczynski.stapi.model.conflict.query.ConflictQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractConflictTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ConflictRepositoryImplTest extends AbstractConflictTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ConflictQueryBuilderFactory conflictQueryBuilderFactoryMock

	private ConflictRepositoryImpl conflictRepositoryImpl

	private QueryBuilder<Conflict> conflictQueryBuilder

	private Pageable pageable

	private ConflictRequestDTO conflictRequestDTO

	private Conflict conflict

	private Page page

	void setup() {
		conflictQueryBuilderFactoryMock = Mock()
		conflictRepositoryImpl = new ConflictRepositoryImpl(conflictQueryBuilderFactoryMock)
		conflictQueryBuilder = Mock()
		pageable = Mock()
		conflictRequestDTO = Mock()
		conflict = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = conflictRepositoryImpl.findMatching(conflictRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * conflictQueryBuilderFactoryMock.createQueryBuilder(pageable) >> conflictQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * conflictRequestDTO.uid >> UID
		1 * conflictQueryBuilder.equal(Conflict_.uid, UID)

		then: 'string criteria are set'
		1 * conflictRequestDTO.name >> NAME
		1 * conflictQueryBuilder.like(Conflict_.name, NAME)

		then: 'integer criteria are set'
		1 * conflictRequestDTO.yearFrom >> YEAR_FROM
		1 * conflictQueryBuilder.between(Conflict_.yearFrom, YEAR_FROM, null)
		1 * conflictRequestDTO.yearTo >> YEAR_TO
		1 * conflictQueryBuilder.between(Conflict_.yearTo, null, YEAR_TO)

		then: 'boolean criteria are set'
		1 * conflictRequestDTO.earthConflict >> EARTH_CONFLICT
		1 * conflictQueryBuilder.equal(Conflict_.earthConflict, EARTH_CONFLICT)
		1 * conflictRequestDTO.federationWar >> FEDERATION_WAR
		1 * conflictQueryBuilder.equal(Conflict_.federationWar, FEDERATION_WAR)
		1 * conflictRequestDTO.klingonWar >> KLINGON_WAR
		1 * conflictQueryBuilder.equal(Conflict_.klingonWar, KLINGON_WAR)
		1 * conflictRequestDTO.dominionWarBattle >> DOMINION_WAR_BATTLE
		1 * conflictQueryBuilder.equal(Conflict_.dominionWarBattle, DOMINION_WAR_BATTLE)
		1 * conflictRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * conflictQueryBuilder.equal(Conflict_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * conflictRequestDTO.sort >> SORT
		1 * conflictQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * conflictQueryBuilder.fetch(Conflict_.locations, true)
		1 * conflictQueryBuilder.fetch(Conflict_.firstSideBelligerents, true)
		1 * conflictQueryBuilder.fetch(Conflict_.firstSideCommanders, true)
		1 * conflictQueryBuilder.fetch(Conflict_.secondSideBelligerents, true)
		1 * conflictQueryBuilder.fetch(Conflict_.secondSideCommanders, true)

		then: 'page is retrieved'
		1 * conflictQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = conflictRepositoryImpl.findMatching(conflictRequestDTO, pageable)

		then:
		1 * conflictQueryBuilderFactoryMock.createQueryBuilder(pageable) >> conflictQueryBuilder

		then: 'uid criteria is set to null'
		1 * conflictRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * conflictQueryBuilder.fetch(Conflict_.locations, false)
		1 * conflictQueryBuilder.fetch(Conflict_.firstSideBelligerents, false)
		1 * conflictQueryBuilder.fetch(Conflict_.firstSideCommanders, false)
		1 * conflictQueryBuilder.fetch(Conflict_.secondSideBelligerents, false)
		1 * conflictQueryBuilder.fetch(Conflict_.secondSideCommanders, false)

		then: 'page is searched for and returned'
		1 * conflictQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(conflict)
		1 * conflict.setLocations(Sets.newHashSet())
		1 * conflict.setFirstSideBelligerents(Sets.newHashSet())
		1 * conflict.setFirstSideCommanders(Sets.newHashSet())
		1 * conflict.setSecondSideBelligerents(Sets.newHashSet())
		1 * conflict.setSecondSideCommanders(Sets.newHashSet())
		pageOutput == page
	}

}
