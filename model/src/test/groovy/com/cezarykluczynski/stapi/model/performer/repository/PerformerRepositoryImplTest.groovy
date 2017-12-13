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

	void setup() {
		performerQueryBuilderMock = Mock()
		performerRepositoryImpl = new PerformerRepositoryImpl(performerQueryBuilderMock)
		performerQueryBuilder = Mock()
		pageable = Mock()
		performerRequestDTO = Mock()
		performer = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'uid criteria is set'
		1 * performerRequestDTO.uid >> UID
		1 * performerQueryBuilder.equal(Performer_.uid, UID)

		then: 'string criteria are set'
		1 * performerRequestDTO.name >> NAME
		1 * performerQueryBuilder.like(Performer_.name, NAME)
		1 * performerRequestDTO.birthName >> BIRTH_NAME
		1 * performerQueryBuilder.like(Performer_.birthName, BIRTH_NAME)
		1 * performerRequestDTO.placeOfBirth >> PLACE_OF_BIRTH
		1 * performerQueryBuilder.like(Performer_.placeOfBirth, PLACE_OF_BIRTH)
		1 * performerRequestDTO.placeOfDeath >> PLACE_OF_DEATH
		1 * performerQueryBuilder.like(Performer_.placeOfDeath, PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * performerRequestDTO.dateOfBirthFrom >> DATE_OF_BIRTH_FROM
		1 * performerRequestDTO.dateOfBirthTo >> DATE_OF_BIRTH_TO
		1 * performerQueryBuilder.between(Performer_.dateOfBirth, DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * performerRequestDTO.dateOfDeathFrom >> DATE_OF_DEATH_FROM
		1 * performerRequestDTO.dateOfDeathTo >> DATE_OF_DEATH_TO
		1 * performerQueryBuilder.between(Performer_.dateOfDeath, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * performerRequestDTO.gender >> GENDER
		1 * performerQueryBuilder.equal(Performer_.gender, GENDER)

		then: 'boolean criteria are set'
		1 * performerRequestDTO.animalPerformer >> ANIMAL_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.animalPerformer, ANIMAL_PERFORMER)
		1 * performerRequestDTO.disPerformer >> DIS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.disPerformer, DIS_PERFORMER)
		1 * performerRequestDTO.ds9Performer >> DS9_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.ds9Performer, DS9_PERFORMER)
		1 * performerRequestDTO.entPerformer >> ENT_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.entPerformer, ENT_PERFORMER)
		1 * performerRequestDTO.filmPerformer >> FILM_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.filmPerformer, FILM_PERFORMER)
		1 * performerRequestDTO.standInPerformer >> STAND_IN_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.standInPerformer, STAND_IN_PERFORMER)
		1 * performerRequestDTO.stuntPerformer >> STUNT_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.stuntPerformer, STUNT_PERFORMER)
		1 * performerRequestDTO.tasPerformer >> TAS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tasPerformer, TAS_PERFORMER)
		1 * performerRequestDTO.tngPerformer >> TNG_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tngPerformer, TNG_PERFORMER)
		1 * performerRequestDTO.tosPerformer >> TOS_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.tosPerformer, TOS_PERFORMER)
		1 * performerRequestDTO.videoGamePerformer >> VIDEO_GAME_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.videoGamePerformer, VIDEO_GAME_PERFORMER)
		1 * performerRequestDTO.voicePerformer >> VOICE_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.voicePerformer, VOICE_PERFORMER)
		1 * performerRequestDTO.voyPerformer >> VOY_PERFORMER
		1 * performerQueryBuilder.equal(Performer_.voyPerformer, VOY_PERFORMER)

		then: 'sort is set'
		1 * performerRequestDTO.sort >> SORT
		1 * performerQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * performerQueryBuilder.fetch(Performer_.episodesPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.episodesStandInPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.episodesStuntPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.moviesPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.moviesStandInPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.moviesStuntPerformances, true)
		1 * performerQueryBuilder.fetch(Performer_.characters, true)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page
		0 * page.content

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'uid criteria is set to null'
		1 * performerRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * performerQueryBuilder.fetch(Performer_.episodesPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.episodesStandInPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.episodesStuntPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.moviesPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.moviesStandInPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.moviesStuntPerformances, false)
		1 * performerQueryBuilder.fetch(Performer_.characters, false)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(performer)
		1 * performer.setEpisodesPerformances(Sets.newHashSet())
		1 * performer.setEpisodesStuntPerformances(Sets.newHashSet())
		1 * performer.setEpisodesStandInPerformances(Sets.newHashSet())
		1 * performer.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
