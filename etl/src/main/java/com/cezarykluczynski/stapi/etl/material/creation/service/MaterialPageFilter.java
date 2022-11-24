package com.cezarykluczynski.stapi.etl.material.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Biohazard", "Bomb", "Class 10 photon torpedo", "Class 5 torpedo",
			"Class 6 warhead", "Class 9 torpedo", "Cobalt bomb", "Cure", "Deficiency", "Fat Man", "Ferengi gas canister", "Honey", "Make-up",
			"Medicinal brew", "Medicinal tea", "Milk", "Mine", "Missile", "Nerve gas", "Photon charge", "Photon grenade", "Photon torpedo",
			"Photonic charge", "Photonic missile", "Photonic torpedo", "Photonic warhead", "Photonic weapon", "Plasma bomb", "Plasma charge",
			"Plasma grenade", "Plasma residue", "Plasma torpedo", "Plasma warhead", "Psychoactive drug", "Psychotropic drug", "Pulse mine",
			"Pulse wave torpedo", "Rama leaf", "Smoking", "Spatial charge", "Spatial torpedo", "Targ milk", "Tricobalt device", "Triton class",
			"Type 6 photon torpedo", "Warhead", "Warp bomb");

	private final CategorySortingService categorySortingService;

	public MaterialPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}
