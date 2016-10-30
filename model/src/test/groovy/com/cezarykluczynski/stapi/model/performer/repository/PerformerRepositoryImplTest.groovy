package com.cezarykluczynski.stapi.model.performer.repository

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuiler
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class PerformerRepositoryImplTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'

	private static final Boolean ANIMAL_PERFORMER = LogicUtil.nextBoolean()
	private static final Boolean DIS_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean DS9_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean ENT_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean FILM_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean STAND_IN_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean STUNT_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean TAS_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean TNG_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean TOS_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean VIDEO_GAME_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean VOICE_PERFORMER =  LogicUtil.nextBoolean()
	private static final Boolean VOY_PERFORMER =  LogicUtil.nextBoolean()

	private PerformerQueryBuiler performerQueryBuilerMock

	private PerformerRepositoryImpl performerRepositoryImpl

	private QueryBuilder<Performer> performerQueryBuilder

	private Pageable pageable

	private PerformerRequestDTO performerRequestDTO

	private Page page

	def setup() {
		performerQueryBuilerMock = Mock(PerformerQueryBuiler)
		performerRepositoryImpl = new PerformerRepositoryImpl(performerQueryBuilerMock)
		performerQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		performerRequestDTO = Mock(PerformerRequestDTO)
		page = Mock(Page)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilerMock.createQueryBuilder(pageable) >> performerQueryBuilder

		1 * performerRequestDTO.getName() >> NAME
		1 * performerQueryBuilder.like("name", NAME)
		1 * performerRequestDTO.getBirthName() >> BIRTH_NAME
		1 * performerQueryBuilder.like("birthName", BIRTH_NAME)
		1 * performerRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * performerQueryBuilder.like("placeOfBirth", PLACE_OF_BIRTH)
		1 * performerRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * performerQueryBuilder.like("placeOfDeath", PLACE_OF_DEATH)
		1 * performerQueryBuilder.search() >> page
		pageOutput == page
	}

}
