package com.cezarykluczynski.stapi.etl.material.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Biohazard", "Bomb", "Class 10 photon torpedo", "Class 5 torpedo",
			"Class 6 warhead", "Class 9 torpedo", "Cobalt bomb", "Cure", "Deficiency", "Fat Man", "Ferengi gas canister", "Honey", "Make-up",
			"Medicinal brew", "Medicinal tea", "Milk", "Mine", "Missile", "Nerve gas", "Photon charge", "Photon grenade", "Photon torpedo",
			"Photonic charge", "Photonic missile", "Photonic torpedo", "Photonic warhead", "Photonic weapon", "Plasma bomb", "Plasma charge",
			"Plasma grenade", "Plasma residue", "Plasma torpedo", "Plasma warhead", "Psychoactive drug", "Psychotropic drug", "Pulse mine",
			"Pulse wave torpedo", "Rama leaf", "Smoking", "Spatial charge", "Spatial torpedo", "Targ milk", "Tricobalt device", "Triton class",
			"Type 6 photon torpedo", "Warhead", "Warp bomb");

	@Getter
	private final CategorySortingService categorySortingService;

	public MaterialPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
