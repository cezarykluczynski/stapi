package com.cezarykluczynski.stapi.model.species.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.entity.Species_
import com.cezarykluczynski.stapi.model.species.query.SpeciesQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SpeciesRepositoryImplTest extends AbstractSpeciesTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SpeciesQueryBuilderFactory speciesQueryBuilderMock

	private SpeciesRepositoryImpl speciesRepositoryImpl

	private QueryBuilder<Species> speciesQueryBuilder

	private Pageable pageable

	private SpeciesRequestDTO speciesRequestDTO

	private Species species

	private Page page

	void setup() {
		speciesQueryBuilderMock = Mock(SpeciesQueryBuilderFactory)
		speciesRepositoryImpl = new SpeciesRepositoryImpl(speciesQueryBuilderMock)
		speciesQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		speciesRequestDTO = Mock(SpeciesRequestDTO)
		page = Mock(Page)
		species = Mock(Species)
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = speciesRepositoryImpl.findMatching(speciesRequestDTO, pageable)

		then:
		1 * speciesQueryBuilderMock.createQueryBuilder(pageable) >> speciesQueryBuilder

		then: 'guid criteria is set'
		1 * speciesRequestDTO.guid >> GUID
		1 * speciesQueryBuilder.equal(Species_.guid, GUID)

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

		then: 'page is searched for and returned'
		1 * speciesQueryBuilder.findPage() >> page
		0 * page.content
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = speciesRepositoryImpl.findMatching(speciesRequestDTO, pageable)

		then:
		1 * speciesQueryBuilderMock.createQueryBuilder(pageable) >> speciesQueryBuilder

		then: 'guid criteria is set to null'
		1 * speciesRequestDTO.guid >> null

		then: 'fetch is performed'
		1 * speciesQueryBuilder.fetch(Species_.homeworld)
		1 * speciesQueryBuilder.fetch(Species_.quadrant)

		then: 'page is searched for and returned'
		1 * speciesQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(species)
		pageOutput == page
	}

}
