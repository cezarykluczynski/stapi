package com.cezarykluczynski.stapi.model.performer.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.entity.Performer_
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PerformerRepositoryImplTest extends AbstractRealWorldPersonTest {

	private static final Gender GENDER = Gender.F
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private PerformerQueryBuilderFactory performerQueryBuilderMock

	private PerformerRepositoryImpl performerRepositoryImpl

	private QueryBuilder<Performer> performerQueryBuilder

	private Pageable pageable

	private PerformerRequestDTO performerRequestDTO

	private Performer performer

	private Page page

	def setup() {
		performerQueryBuilderMock = Mock(PerformerQueryBuilderFactory)
		performerRepositoryImpl = new PerformerRepositoryImpl(performerQueryBuilderMock)
		performerQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		performerRequestDTO = Mock(PerformerRequestDTO)
		page = Mock(Page)
		performer = Mock(Performer)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'guid criteria is set'
		1 * performerRequestDTO.getGuid() >> GUID
		1 * performerQueryBuilder.equal(Performer_.guid, GUID)

		then: 'string criteria are set'
		1 * performerRequestDTO.getName() >> NAME
		1 * performerQueryBuilder.like(Performer_.name, NAME)
		1 * performerRequestDTO.getBirthName() >> BIRTH_NAME
		1 * performerQueryBuilder.like(Performer_.birthName, BIRTH_NAME)
		1 * performerRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * performerQueryBuilder.like(Performer_.placeOfBirth, PLACE_OF_BIRTH)
		1 * performerRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * performerQueryBuilder.like(Performer_.placeOfDeath, PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * performerRequestDTO.getDateOfBirthFrom() >> DATE_OF_BIRTH_FROM
		1 * performerRequestDTO.getDateOfBirthTo() >> DATE_OF_BIRTH_TO
		1 * performerQueryBuilder.between(Performer_.dateOfBirth, DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * performerRequestDTO.getDateOfDeathFrom() >> DATE_OF_DEATH_FROM
		1 * performerRequestDTO.getDateOfDeathTo() >> DATE_OF_DEATH_TO
		1 * performerQueryBuilder.between(Performer_.dateOfDeath, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * performerRequestDTO.getGender() >> GENDER
		1 * performerQueryBuilder.equal(Performer_.gender, GENDER)

		then: 'boolean criteria are set'
		1 * performerRequestDTO.getAnimalPerformer() >> ANIMAL_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.animalPerformer, ANIMAL_PERFORMER)
		1 * performerRequestDTO.getDisPerformer() >> DIS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.disPerformer, DIS_PERFORMER)
		1 * performerRequestDTO.getDs9Performer() >> DS9_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.ds9Performer, DS9_PERFORMER)
		1 * performerRequestDTO.getEntPerformer() >> ENT_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.entPerformer, ENT_PERFORMER)
		1 * performerRequestDTO.getFilmPerformer() >> FILM_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.filmPerformer, FILM_PERFORMER)
		1 * performerRequestDTO.getStandInPerformer() >> STAND_IN_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.standInPerformer, STAND_IN_PERFORMER)
		1 * performerRequestDTO.getStuntPerformer() >> STUNT_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.stuntPerformer, STUNT_PERFORMER)
		1 * performerRequestDTO.getTasPerformer() >> TAS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tasPerformer, TAS_PERFORMER)
		1 * performerRequestDTO.getTngPerformer() >> TNG_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tngPerformer, TNG_PERFORMER)
		1 * performerRequestDTO.getTosPerformer() >> TOS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tosPerformer, TOS_PERFORMER)
		1 * performerRequestDTO.getVideoGamePerformer() >> VIDEO_GAME_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.videoGamePerformer, VIDEO_GAME_PERFORMER)
		1 * performerRequestDTO.getVoicePerformer() >> VOICE_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.voicePerformer, VOICE_PERFORMER)
		1 * performerRequestDTO.getVoyPerformer() >> VOY_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.voyPerformer, VOY_PERFORMER)

		then: 'sort is set'
		1 * performerRequestDTO.getSort() >> SORT
		1 * performerQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * performerQueryBuilder.fetch(Performer_.characters, true)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page
		0 * page.getContent()
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	def "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'guid criteria is set to null'
		1 * performerRequestDTO.getGuid() >> null

		then: 'fetch is performed with false flag'
		1 * performerQueryBuilder.fetch(Performer_.characters, false)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.getContent() >> Lists.newArrayList(performer)
		1 * performer.setPerformances(Sets.newHashSet())
		1 * performer.setStuntPerformances(Sets.newHashSet())
		1 * performer.setStandInPerformances(Sets.newHashSet())
		1 * performer.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
