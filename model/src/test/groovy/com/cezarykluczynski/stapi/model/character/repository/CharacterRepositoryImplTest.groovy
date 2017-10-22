package com.cezarykluczynski.stapi.model.character.repository

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation_
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.character.entity.Character_
import com.cezarykluczynski.stapi.model.character.query.CharacterInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.character.query.CharacterQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class CharacterRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	private CharacterInitialQueryBuilderFactory characterInitialQueryBuilderFactoryMock

	private CharacterQueryBuilderFactory characterQueryBuilderMock

	private CharacterRepositoryImpl characterRepositoryImpl

	private QueryBuilder<Character> characterQueryBuilder

	private QueryBuilder<Character> characterSpeciesAndRelationsQueryBuilder

	private QueryBuilder<Character> titlesAndOrganizationsQueryBuilder

	private Pageable pageable

	private CharacterRequestDTO characterRequestDTO

	private Character character

	private Character characterSpeciesAndRelationsCharacter

	private Character titlesAndOrganizationsCharacter

	private Page page

	private Set<CharacterSpecies> characterSpeciesSet

	private Set<CharacterRelation> characterRelationSet

	private Set<Title> titleSet

	private Set<Occupation> occupationSet

	private Set<Organization> organizationSet

	void setup() {
		characterInitialQueryBuilderFactoryMock = Mock()
		characterRepositoryImpl = new CharacterRepositoryImpl(characterInitialQueryBuilderFactoryMock)
		characterQueryBuilderMock = Mock()
		characterSpeciesAndRelationsQueryBuilder = Mock()
		titlesAndOrganizationsQueryBuilder = Mock()
		characterQueryBuilder = Mock()
		pageable = Mock()
		characterRequestDTO = Mock()
		page = Mock()
		character = Mock()
		characterSpeciesAndRelationsCharacter = Mock()
		titlesAndOrganizationsCharacter = Mock()
		characterSpeciesSet = Mock()
		characterRelationSet = Mock()
		titleSet = Mock()
		occupationSet = Mock()
		organizationSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = characterRepositoryImpl.findMatching(characterRequestDTO, pageable)

		then:
		1 * characterInitialQueryBuilderFactoryMock.createInitialQueryBuilder(characterRequestDTO, pageable) >> characterQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * characterRequestDTO.uid >> UID

		then: 'performers, episodes, and movies fetch is performed'
		1 * characterQueryBuilder.fetch(Character_.performers)
		1 * characterQueryBuilder.fetch(Character_.episodes)
		1 * characterQueryBuilder.fetch(Character_.movies)
		1 * characterQueryBuilder.fetch(Character_.movies, Movie_.mainDirector)

		then: 'page is retrieved'
		1 * characterQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(character)

		then: 'another criteria builder is retrieved for character species and relations'
		1 * characterInitialQueryBuilderFactoryMock.createInitialQueryBuilder(characterRequestDTO, pageable) >> characterSpeciesAndRelationsQueryBuilder

		then: 'character species and character relations fetch is performed'
		1 * characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterSpecies)
		1 * characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterRelations)
		1 * characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterRelations, CharacterRelation_.source)
		1 * characterSpeciesAndRelationsQueryBuilder.fetch(Character_.characterRelations, CharacterRelation_.target)

		then: 'result list is retrieved'
		1 * characterSpeciesAndRelationsQueryBuilder.findAll() >> Lists.newArrayList(characterSpeciesAndRelationsCharacter)

		then: 'entities are set to base character'
		1 * characterSpeciesAndRelationsCharacter.characterSpecies >> characterSpeciesSet
		1 * character.setCharacterSpecies(characterSpeciesSet)
		1 * characterSpeciesAndRelationsCharacter.characterRelations >> characterRelationSet
		1 * character.setCharacterRelations(characterRelationSet)

		then: 'another criteria builder is retrieved for titles and organizations'
		1 * characterInitialQueryBuilderFactoryMock.createInitialQueryBuilder(characterRequestDTO, pageable) >> titlesAndOrganizationsQueryBuilder

		then: 'character species and character relations fetch is performed'
		1 * titlesAndOrganizationsQueryBuilder.fetch(Character_.titles)
		1 * titlesAndOrganizationsQueryBuilder.fetch(Character_.occupations)
		1 * titlesAndOrganizationsQueryBuilder.fetch(Character_.organizations)

		then: 'result list is retrieved'
		1 * titlesAndOrganizationsQueryBuilder.findAll() >> Lists.newArrayList(titlesAndOrganizationsCharacter)

		then: 'entities are set to base character'
		1 * titlesAndOrganizationsCharacter.titles >> titleSet
		1 * character.setTitles(titleSet)
		1 * titlesAndOrganizationsCharacter.occupations >> occupationSet
		1 * character.setOccupations(occupationSet)
		1 * titlesAndOrganizationsCharacter.organizations >> organizationSet
		1 * character.setOrganizations(organizationSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = characterRepositoryImpl.findMatching(characterRequestDTO, pageable)

		then:
		1 * characterInitialQueryBuilderFactoryMock.createInitialQueryBuilder(characterRequestDTO, pageable) >> characterQueryBuilder

		then: 'uid criteria is set to null'
		1 * characterRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * characterQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(character)
		1 * character.setPerformers(Sets.newHashSet())
		1 * character.setEpisodes(Sets.newHashSet())
		1 * character.setMovies(Sets.newHashSet())
		1 * character.setCharacterSpecies(Sets.newHashSet())
		1 * character.setCharacterRelations(Sets.newHashSet())
		1 * character.setTitles(Sets.newHashSet())
		1 * character.setOccupations(Sets.newHashSet())
		1 * character.setOrganizations(Sets.newHashSet())
		pageOutput == page
	}

}
