package com.cezarykluczynski.stapi.model.species.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.entity.Species_;
import com.cezarykluczynski.stapi.model.species.query.SpeciesQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class SpeciesRepositoryImpl extends AbstractRepositoryImpl<Species> implements SpeciesRepositoryCustom {

	private SpeciesQueryBuilderFactory speciesQueryBuilderFactory;

	@Inject
	public SpeciesRepositoryImpl(SpeciesQueryBuilderFactory speciesQueryBuilderFactory) {
		this.speciesQueryBuilderFactory = speciesQueryBuilderFactory;
	}

	@Override
	public Page<Species> findMatching(SpeciesRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Species> speciesQueryBuilder = speciesQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();

		speciesQueryBuilder.equal(Species_.guid, guid);
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
		speciesQueryBuilder.fetch(Species_.quadrant);

		boolean doFetch = guid != null;
		Page<Species> performerPage = speciesQueryBuilder.findPage();
		clearProxies(performerPage, !doFetch);
		return performerPage;
	}

	@Override
	protected void clearProxies(Page<Species> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(species -> {
			// TODO
		});
	}

}
