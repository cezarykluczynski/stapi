package com.cezarykluczynski.stapi.model.character.query

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.Character_
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class CharacterInitialQueryBuilderFactoryTest extends Specification {

	private static final String UID = 'ABCD0123456789'
	private static final String NAME = 'NAME'
	private static final Gender GENDER = Gender.F
	private static final Boolean DECEASED = RandomUtil.nextBoolean()
	private static final Boolean HOLOGRAM = RandomUtil.nextBoolean()
	private static final Boolean FICTIONAL_CHARACTER = RandomUtil.nextBoolean()
	private static final Boolean MIRROR = RandomUtil.nextBoolean()
	private static final Boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private CharacterQueryBuilderFactory characterQueryBuilderMock

	private CharacterInitialQueryBuilderFactory characterInitialQueryBuilderFactory

	private QueryBuilder<Character> characterQueryBuilder

	private Pageable pageable

	private CharacterRequestDTO characterRequestDTO

	private Character character

	private Page page

	void setup() {
		characterQueryBuilderMock = Mock()
		characterInitialQueryBuilderFactory = new CharacterInitialQueryBuilderFactory(characterQueryBuilderMock)
		characterQueryBuilder = Mock()
		pageable = Mock()
		characterRequestDTO = Mock()
		page = Mock()
		character = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<Character> characterQueryBuilderOutput = characterInitialQueryBuilderFactory
				.createInitialQueryBuilder(characterRequestDTO, pageable)

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
		1 * characterRequestDTO.hologram >> HOLOGRAM
		1 * characterQueryBuilder.equal(Character_.hologram, HOLOGRAM)
		1 * characterRequestDTO.fictionalCharacter >> FICTIONAL_CHARACTER
		1 * characterQueryBuilder.equal(Character_.fictionalCharacter, FICTIONAL_CHARACTER)
		1 * characterRequestDTO.mirror >> MIRROR
		1 * characterQueryBuilder.equal(Character_.mirror, MIRROR)
		1 * characterRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * characterQueryBuilder.equal(Character_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * characterRequestDTO.sort >> SORT
		1 * characterQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		characterQueryBuilderOutput == characterQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
