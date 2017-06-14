package com.cezarykluczynski.stapi.model.species.repository

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject_
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.character.repository.CharacterSpeciesRepository
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.entity.Species_
import com.cezarykluczynski.stapi.model.species.query.SpeciesQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SpeciesRepositoryImplTest extends AbstractSpeciesTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SpeciesQueryBuilderFactory speciesQueryBuilderMock

	private CharacterSpeciesRepository characterSpeciesRepositoryMock

	private CharacterRepository characterRepositoryMock

	private SpeciesRepositoryImpl speciesRepositoryImpl

	private QueryBuilder<Species> speciesQueryBuilder

	private Pageable pageable

	private SpeciesRequestDTO speciesRequestDTO

	private Species species

	private Page page

	void setup() {
		speciesQueryBuilderMock = Mock()
		characterSpeciesRepositoryMock = Mock()
		characterRepositoryMock = Mock()
		speciesRepositoryImpl = new SpeciesRepositoryImpl(speciesQueryBuilderMock, characterSpeciesRepositoryMock, characterRepositoryMock)
		speciesQueryBuilder = Mock()
		pageable = Mock()
		speciesRequestDTO = Mock()
		page = Mock()
		species = new Species()
	}

	void "query is built and performed"() {
		given:
		Set<CharacterSpecies> characterSpeciesSet  = Mock()
		Character character1 = Mock()
		Character character2 = Mock()

		when:
		Page pageOutput = speciesRepositoryImpl.findMatching(speciesRequestDTO, pageable)

		then:
		1 * speciesQueryBuilderMock.createQueryBuilder(pageable) >> speciesQueryBuilder

		then: 'uid criteria is set'
		1 * speciesRequestDTO.uid >> UID
		1 * speciesQueryBuilder.equal(Species_.uid, UID)

		then: 'string criteria are set'
		1 * speciesRequestDTO.name >> NAME
		1 * speciesQueryBuilder.like(Species_.name, NAME)

		then: 'boolean criteria are set'
		1 * speciesRequestDTO.extinctSpecies >> EXTINCT_SPECIES
		1 * speciesQueryBuilder.equal(Species_.extinctSpecies, EXTINCT_SPECIES)
		1 * speciesRequestDTO.warpCapableSpecies >> WARP_CAPABLE_SPECIES
		1 * speciesQueryBuilder.equal(Species_.warpCapableSpecies, WARP_CAPABLE_SPECIES)
		1 * speciesRequestDTO.extraGalacticSpecies >> EXTRA_GALACTIC_SPECIES
		1 * speciesQueryBuilder.equal(Species_.extraGalacticSpecies, EXTRA_GALACTIC_SPECIES)
		1 * speciesRequestDTO.humanoidSpecies >> HUMANOID_SPECIES
		1 * speciesQueryBuilder.equal(Species_.humanoidSpecies, HUMANOID_SPECIES)
		1 * speciesRequestDTO.reptilianSpecies >> REPTILIAN_SPECIES
		1 * speciesQueryBuilder.equal(Species_.reptilianSpecies, REPTILIAN_SPECIES)
		1 * speciesRequestDTO.nonCorporealSpecies >> NON_CORPOREAL_SPECIES
		1 * speciesQueryBuilder.equal(Species_.nonCorporealSpecies, NON_CORPOREAL_SPECIES)
		1 * speciesRequestDTO.shapeshiftingSpecies >> SHAPESHIFTING_SPECIES
		1 * speciesQueryBuilder.equal(Species_.shapeshiftingSpecies, SHAPESHIFTING_SPECIES)
		1 * speciesRequestDTO.spaceborneSpecies >> SPACEBORNE_SPECIES
		1 * speciesQueryBuilder.equal(Species_.spaceborneSpecies, SPACEBORNE_SPECIES)
		1 * speciesRequestDTO.telepathicSpecies >> TELEPATHIC_SPECIES
		1 * speciesQueryBuilder.equal(Species_.telepathicSpecies, TELEPATHIC_SPECIES)
		1 * speciesRequestDTO.transDimensionalSpecies >> TRANS_DIMENSIONAL_SPECIES
		1 * speciesQueryBuilder.equal(Species_.transDimensionalSpecies, TRANS_DIMENSIONAL_SPECIES)
		1 * speciesRequestDTO.unnamedSpecies >> UNNAMED_SPECIES
		1 * speciesQueryBuilder.equal(Species_.unnamedSpecies, UNNAMED_SPECIES)
		1 * speciesRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * speciesQueryBuilder.equal(Species_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * speciesRequestDTO.sort >> SORT
		1 * speciesQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * speciesQueryBuilder.fetch(Species_.homeworld)
		1 * speciesQueryBuilder.fetch(Species_.quadrant)
		1 * speciesQueryBuilder.fetch(Species_.homeworld, AstronomicalObject_.location, true)
		1 * speciesQueryBuilder.fetch(Species_.quadrant, AstronomicalObject_.location, true)

		then: 'page is searched for'
		1 * speciesQueryBuilder.findPage() >> page
		0 * page.content

		then: 'characters are fetched'
		1 * page.totalElements >> 1
		1 * page.content >> Lists.newArrayList(species)
		1 * characterSpeciesRepositoryMock.findBySpecies(species) >> characterSpeciesSet
		1 * characterRepositoryMock.findByCharacterSpeciesIn(characterSpeciesSet) >> Sets.newHashSet(character1, character2)
		species.characters.contains character1
		species.characters.contains character2

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "characters and location headers are not fetched when UID is not provided"() {
		when:
		Page pageOutput = speciesRepositoryImpl.findMatching(speciesRequestDTO, pageable)

		then:
		1 * speciesQueryBuilderMock.createQueryBuilder(pageable) >> speciesQueryBuilder

		then: 'uid criteria is set to null'
		1 * speciesRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * speciesQueryBuilder.fetch(Species_.homeworld, AstronomicalObject_.location, false)
		1 * speciesQueryBuilder.fetch(Species_.quadrant, AstronomicalObject_.location, false)

		then: 'page is searched for and returned'
		1 * speciesQueryBuilder.findPage() >> page

		then: 'other repositories are not interacted with'
		0 * characterSpeciesRepositoryMock._
		0 * characterRepositoryMock._

		then: 'page is returned'
		pageOutput == page
	}

}
