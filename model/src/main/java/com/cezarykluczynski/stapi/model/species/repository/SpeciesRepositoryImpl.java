package com.cezarykluczynski.stapi.model.species.repository;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject_;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.character.repository.CharacterSpeciesRepository;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.entity.Species_;
import com.cezarykluczynski.stapi.model.species.query.SpeciesQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SpeciesRepositoryImpl implements SpeciesRepositoryCustom {

	private final SpeciesQueryBuilderFactory speciesQueryBuilderFactory;

	private final CharacterSpeciesRepository characterSpeciesRepository;

	private final CharacterRepository characterRepository;

	public SpeciesRepositoryImpl(SpeciesQueryBuilderFactory speciesQueryBuilderFactory, CharacterSpeciesRepository characterSpeciesRepository,
			CharacterRepository characterRepository) {
		this.speciesQueryBuilderFactory = speciesQueryBuilderFactory;
		this.characterSpeciesRepository = characterSpeciesRepository;
		this.characterRepository = characterRepository;
	}

	@Override
	public Page<Species> findMatching(SpeciesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Species> speciesQueryBuilder = speciesQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		speciesQueryBuilder.equal(Species_.uid, uid);
		speciesQueryBuilder.like(Species_.name, criteria.getName());
		speciesQueryBuilder.equal(Species_.extinctSpecies, criteria.getExtinctSpecies());
		speciesQueryBuilder.equal(Species_.warpCapableSpecies, criteria.getWarpCapableSpecies());
		speciesQueryBuilder.equal(Species_.extraGalacticSpecies, criteria.getExtraGalacticSpecies());
		speciesQueryBuilder.equal(Species_.humanoidSpecies, criteria.getHumanoidSpecies());
		speciesQueryBuilder.equal(Species_.reptilianSpecies, criteria.getReptilianSpecies());
		speciesQueryBuilder.equal(Species_.nonCorporealSpecies, criteria.getNonCorporealSpecies());
		speciesQueryBuilder.equal(Species_.shapeshiftingSpecies, criteria.getShapeshiftingSpecies());
		speciesQueryBuilder.equal(Species_.spaceborneSpecies, criteria.getSpaceborneSpecies());
		speciesQueryBuilder.equal(Species_.telepathicSpecies, criteria.getTelepathicSpecies());
		speciesQueryBuilder.equal(Species_.transDimensionalSpecies, criteria.getTransDimensionalSpecies());
		speciesQueryBuilder.equal(Species_.unnamedSpecies, criteria.getUnnamedSpecies());
		speciesQueryBuilder.equal(Species_.alternateReality, criteria.getAlternateReality());
		speciesQueryBuilder.setSort(criteria.getSort());
		speciesQueryBuilder.fetch(Species_.homeworld);
		speciesQueryBuilder.fetch(Species_.homeworld, AstronomicalObject_.location, doFetch);
		speciesQueryBuilder.fetch(Species_.quadrant);
		speciesQueryBuilder.fetch(Species_.quadrant, AstronomicalObject_.location, doFetch);

		Page<Species> performerPage = speciesQueryBuilder.findPage();
		fetchCharacters(performerPage, doFetch);
		return performerPage;
	}

	private void fetchCharacters(Page<Species> performerPage, boolean doFetch) {
		if (!doFetch || performerPage.getTotalElements() != 1) {
			return;
		}

		Species species = performerPage.getContent().get(0);
		Set<CharacterSpecies> characterSpecies = characterSpeciesRepository.findBySpecies(species);
		Set<Character> characterSet = characterRepository.findByCharacterSpeciesIn(characterSpecies);
		species.getCharacters().addAll(characterSet);
	}

}
