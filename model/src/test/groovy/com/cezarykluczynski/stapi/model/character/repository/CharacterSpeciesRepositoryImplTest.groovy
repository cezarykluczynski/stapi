package com.cezarykluczynski.stapi.model.character.repository

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies_
import com.cezarykluczynski.stapi.model.character.query.CharacterSpeciesQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.google.common.collect.Lists
import org.apache.commons.lang3.math.Fraction
import org.hibernate.Session
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

import javax.persistence.EntityManager

class CharacterSpeciesRepositoryImplTest extends Specification {

	private static final Integer NUMERATOR_INTEGER = 1
	private static final Integer DENOMINATOR_INTEGER = 2
	private static final Long NUMERATOR_LONG = 1L
	private static final Long DENOMINATOR_LONG = 2L

	private static final Long SPECIES_ID = 1

	private CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilderFactoryMock

	private QueryBuilder<CharacterSpecies> characterSpeciesQueryBuilder

	private JpaContext jpaContextMock

	private CharacterSpeciesRepositoryImpl characterSpeciesRepositoryImpl

	void setup() {
		characterSpeciesQueryBuilderFactoryMock = Mock()
		characterSpeciesQueryBuilder = Mock()
		jpaContextMock = Mock()
		characterSpeciesRepositoryImpl = new CharacterSpeciesRepositoryImpl(characterSpeciesQueryBuilderFactoryMock, jpaContextMock)
	}

	void "finds existing entity"() {
		given:
		Fraction fraction = Fraction.getFraction(1, 2)
		Species species = new Species(id: SPECIES_ID)
		CharacterSpecies characterSpecies = new CharacterSpecies()

		when:
		CharacterSpecies characterSpeciesOutput = characterSpeciesRepositoryImpl.findOrCreate(species, fraction)

		then:
		1 * characterSpeciesQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == 1
			characterSpeciesQueryBuilder
		}
		1 * characterSpeciesQueryBuilder.equal(CharacterSpecies_.numerator, NUMERATOR_INTEGER)
		1 * characterSpeciesQueryBuilder.equal(CharacterSpecies_.denominator, DENOMINATOR_INTEGER)
		1 * characterSpeciesQueryBuilder.fetch(CharacterSpecies_.species, false)
		1 * characterSpeciesQueryBuilder.joinPropertyEqual(CharacterSpecies_.species, 'id', SPECIES_ID)
		1 * characterSpeciesQueryBuilder.findAll() >> Lists.newArrayList(characterSpecies)
		0 * _
		characterSpeciesOutput == characterSpecies
	}

	void "creates new entity"() {
		given:
		Fraction fraction = Fraction.getFraction(1, 2)
		Species species = new Species(id: SPECIES_ID)
		CharacterSpecies characterSpecies = new CharacterSpecies()
		EntityManager entityManager = Mock()
		Session session = Mock()

		when:
		CharacterSpecies characterSpeciesOutput = characterSpeciesRepositoryImpl.findOrCreate(species, fraction)

		then:
		1 * characterSpeciesQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == 1
			characterSpeciesQueryBuilder
		}
		1 * characterSpeciesQueryBuilder.equal(CharacterSpecies_.numerator, NUMERATOR_INTEGER)
		1 * characterSpeciesQueryBuilder.equal(CharacterSpecies_.denominator, DENOMINATOR_INTEGER)
		1 * characterSpeciesQueryBuilder.joinPropertyEqual(CharacterSpecies_.species, 'id', SPECIES_ID)
		1 * characterSpeciesQueryBuilder.fetch(CharacterSpecies_.species, false)
		1 * characterSpeciesQueryBuilder.findAll() >> Lists.newArrayList()
		1 * jpaContextMock.getEntityManagerByManagedType(CharacterSpecies) >> entityManager
		1 * entityManager.unwrap(Session) >> session
		1 * session.save(_ as Object) >> characterSpecies
		0 * _
		characterSpeciesOutput.numerator == NUMERATOR_LONG
		characterSpeciesOutput.denominator == DENOMINATOR_LONG
		characterSpeciesOutput.species == species
	}

}
