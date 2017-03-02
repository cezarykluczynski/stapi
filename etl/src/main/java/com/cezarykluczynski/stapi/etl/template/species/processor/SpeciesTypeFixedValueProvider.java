package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesTypeFixedValueProvider implements FixedValueProvider<String, Boolean> {

	private static final List<String> WARP_CAPABLE_SPECIES = Lists.newArrayList(
			"Acamarian", "Ancient insectoid", "Angosian", "Antedian", "Antica", "Ardanan", "Arkonian", "Axanar",
			"Bajoran", "Betazoid", "Bolian", "Breen",
			"Caldonian", "Cardassian", "Catullan", "Chalnoth", "Cheron native", "Coridan",
			"Dekendi", "Deltan", "Denebian", "Denobulan",
			"Enaran", "Evora",
			"Ferengi",
			"Gideon",
			"Kaelon", "Karemma", "Kelvan", "Klaestron", "Klingon", "Kobali", "Krenim",
			"Ledosian", "Lysian",
			"Mizarian",
			"Nocona's species", "Norcadian",
			"Orion",
			"Pandronian", "Peliar Zel native", "Phylosian",
			"Quarren (species)",
			"Reptohumanoid", "Rigelian", "Romulan",
			"Sargon's species", "Selay", "Sikarian", "Suliban",
			"Takret", "Talosian", "Tarellian", "Tellarite", "The Children of Tama", "Tiburonian", "Trill", "Tyrans", "T-Rogoran",
			"Varro", "Vissian", "Vulcan",
			"Yaderan",
			"Zalkonian", "Zibalian", "Zobral's species"
	);

	@Override
	public FixedValueHolder<Boolean> getSearchedValue(String key) {
		boolean found = WARP_CAPABLE_SPECIES.contains(key);
		return FixedValueHolder.of(found, found ? found : null);
	}

}
