package com.cezarykluczynski.stapi.model.character.repository

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.Character_
import com.cezarykluczynski.stapi.model.character.query.CharacterQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class CharacterRepositoryImplTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'
	private static final Gender GENDER = Gender.F
	private static final Boolean DECEASED = LogicUtil.nextBoolean()
	private static final Boolean MIRROR = LogicUtil.nextBoolean()
	private static final Boolean ALTERNATE_REALITY = LogicUtil.nextBoolean()
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private CharacterQueryBuilderFactory characterQueryBuilderMock

	private CharacterRepositoryImpl characterRepositoryImpl

	private QueryBuilder<Character> characterQueryBuilder

	private Pageable pageable

	private CharacterRequestDTO characterRequestDTO

	private Character character

	private Page page

	void setup() {
		characterQueryBuilderMock = Mock()
		characterRepositoryImpl = new CharacterRepositoryImpl(characterQueryBuilderMock)
		characterQueryBuilder = Mock()
		pageable = Mock()
		characterRequestDTO = Mock()
		page = Mock()
		character = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = characterRepositoryImpl.findMatching(characterRequestDTO, pageable)

		then:
		1 * characterQueryBuilderMock.createQueryBuilder(pageable) >> characterQueryBuilder

		then: 'uid criteria is set'
		1 * characterRequestDTO.uid >> UID
		1 * characterQueryBuilder.equal(Character_.uid, UID)

		then: 'string criteria are set'
		1 * characterRequestDTO.name >> NAME
		1 * characterQueryBuilder.like(Character_.name, NAME)

		then: 'enum criteria is set'
		1 * characterRequestDTO.gender >> GENDER
		1 * characterQueryBuilder.equal(Character_.gender, GENDER)

		then: 'boolean criteria are set'
		1 * characterRequestDTO.deceased >> DECEASED
		1 * characterQueryBuilder.equal(Character_.deceased, DECEASED)
		1 * characterRequestDTO.mirror >> MIRROR
		1 * characterQueryBuilder.equal(Character_.mirror, MIRROR)
		1 * characterRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * characterQueryBuilder.equal(Character_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * characterRequestDTO.sort >> SORT
		1 * characterQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * characterQueryBuilder.fetch(Character_.performers, true)
		1 * characterQueryBuilder.fetch(Character_.episodes, true)
		1 * characterQueryBuilder.fetch(Character_.movies, true)
		1 * characterQueryBuilder.fetch(Character_.movies, Movie_.mainDirector, true)
		1 * characterQueryBuilder.fetch(Character_.characterSpecies, true)

		then: 'page is searched for and returned'
		1 * characterQueryBuilder.findPage() >> page
		0 * page.content
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = characterRepositoryImpl.findMatching(characterRequestDTO, pageable)

		then:
		1 * characterQueryBuilderMock.createQueryBuilder(pageable) >> characterQueryBuilder

		then: 'uid criteria is set to null'
		1 * characterRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * characterQueryBuilder.fetch(Character_.performers, false)
		1 * characterQueryBuilder.fetch(Character_.episodes, false)
		1 * characterQueryBuilder.fetch(Character_.movies, false)
		1 * characterQueryBuilder.fetch(Character_.movies, Movie_.mainDirector, false)
		1 * characterQueryBuilder.fetch(Character_.characterSpecies, false)

		then: 'page is searched for and returned'
		1 * characterQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(character)
		1 * character.setPerformers(Sets.newHashSet())
		1 * character.setEpisodes(Sets.newHashSet())
		1 * character.setMovies(Sets.newHashSet())
		1 * character.setCharacterSpecies(Sets.newHashSet())
		pageOutput == page
	}

}
