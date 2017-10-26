package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies_;
import com.cezarykluczynski.stapi.model.character.query.CharacterSpeciesQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.apache.commons.lang3.math.Fraction;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CharacterSpeciesRepositoryImpl implements CharacterSpeciesRepositoryCustom {

	private final CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilderFactory;

	private final JpaContext jpaContext;

	public CharacterSpeciesRepositoryImpl(CharacterSpeciesQueryBuilderFactory characterSpeciesQueryBuilderFactory, JpaContext jpaContext) {
		this.characterSpeciesQueryBuilderFactory = characterSpeciesQueryBuilderFactory;
		this.jpaContext = jpaContext;
	}

	@Override
	public CharacterSpecies findOrCreate(Species species, Fraction fraction) {
		QueryBuilder<CharacterSpecies> characterSpeciesQueryBuilder = characterSpeciesQueryBuilderFactory.createQueryBuilder(new PageRequest(0, 1));
		Long numerator = (long) fraction.getNumerator();
		Long denominator = (long) fraction.getDenominator();

		characterSpeciesQueryBuilder.equal(CharacterSpecies_.numerator, numerator);
		characterSpeciesQueryBuilder.equal(CharacterSpecies_.denominator, denominator);
		characterSpeciesQueryBuilder.fetch(CharacterSpecies_.species, false);
		characterSpeciesQueryBuilder.joinPropertyEqual(CharacterSpecies_.species, "id", species.getId());

		List<CharacterSpecies> characterSpeciesList = characterSpeciesQueryBuilder.findAll();

		if (characterSpeciesList.isEmpty()) {
			CharacterSpecies characterSpecies = new CharacterSpecies();
			characterSpecies.setSpecies(species);
			characterSpecies.setNumerator(numerator);
			characterSpecies.setDenominator(denominator);

			EntityManager entityManager = jpaContext.getEntityManagerByManagedType(CharacterSpecies.class);
			Session session = entityManager.unwrap(Session.class);
			session.save(characterSpecies);

			return characterSpecies;
		} else {
			return characterSpeciesList.get(0);
		}
	}

}
