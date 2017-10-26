package com.cezarykluczynski.stapi.etl.species.creation.processor;

import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SpeciesTemplateProcessor implements ItemProcessor<SpeciesTemplate, Species> {

	private final UidGenerator uidGenerator;

	public SpeciesTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Species process(SpeciesTemplate item) throws Exception {
		Species species = new Species();

		species.setName(item.getName());
		species.setPage(item.getPage());
		species.setUid(uidGenerator.generateFromPage(item.getPage(), Species.class));
		species.setHomeworld(item.getHomeworld());
		species.setQuadrant(item.getQuadrant());
		species.setExtinctSpecies(item.isExtinctSpecies());
		species.setWarpCapableSpecies(item.isWarpCapableSpecies());
		species.setExtraGalacticSpecies(item.isExtraGalacticSpecies());
		species.setHumanoidSpecies(item.isHumanoidSpecies());
		species.setReptilianSpecies(item.isReptilianSpecies());
		species.setNonCorporealSpecies(item.isNonCorporealSpecies());
		species.setShapeshiftingSpecies(item.isShapeshiftingSpecies());
		species.setSpaceborneSpecies(item.isSpaceborneSpecies());
		species.setTelepathicSpecies(item.isTelepathicSpecies());
		species.setTransDimensionalSpecies(item.isTransDimensionalSpecies());
		species.setUnnamedSpecies(item.isUnnamedSpecies());
		species.setAlternateReality(item.isAlternateReality());

		return species;
	}
}
