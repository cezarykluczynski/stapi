package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryPlacesDTO;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IndividualTemplatePlacesFixedValueProvider implements FixedValueProvider<String, IndividualLifeBoundaryPlacesDTO> {

	private static final String EARTH = PageTitle.EARTH;
	private static final String DELTA_VEGA = "Delta Vega";
	private static final String MOJAVE = "Mojave, California, United States of America, Earth";
	private static final String SCOTLAND = "Scotland, Earth";
	private static final String TURKANA_VI = "Turkana IV";

	private static final Map<String, IndividualLifeBoundaryPlacesDTO> PLACES_MAP = Maps.newHashMap();

	static {
		PLACES_MAP.put("Moses", IndividualLifeBoundaryPlacesDTO.of(EARTH, EARTH));
		PLACES_MAP.put("Jonathan Archer", IndividualLifeBoundaryPlacesDTO.of("Upstate New York, North America, Earth", null));
		PLACES_MAP.put("Data", IndividualLifeBoundaryPlacesDTO.of("Omicron Theta", "Scimitar, Bassen Rift"));
		PLACES_MAP.put("Worf", IndividualLifeBoundaryPlacesDTO.of("Qo'noS", null));
		PLACES_MAP.put("David", IndividualLifeBoundaryPlacesDTO.of("Darwin Genetic Research Station, Gagarin IV", null));
		PLACES_MAP.put("Christopher Pike", IndividualLifeBoundaryPlacesDTO.of(MOJAVE, null));
		PLACES_MAP.put("Natasha Yar", IndividualLifeBoundaryPlacesDTO.of(TURKANA_VI, "Vagra II"));
		PLACES_MAP.put("Benjamin Sisko", IndividualLifeBoundaryPlacesDTO.of("New Orleans, Earth", null));
		PLACES_MAP.put("Hoshi Sato", IndividualLifeBoundaryPlacesDTO.of("Kyoto, Japan", null));
		PLACES_MAP.put("James T. Kirk", IndividualLifeBoundaryPlacesDTO.of(null, "Veridian III"));
		PLACES_MAP.put("Krall", IndividualLifeBoundaryPlacesDTO.of("Murrysville, Pennsylvania, Earth", null));
		PLACES_MAP.put("Mardah", IndividualLifeBoundaryPlacesDTO.of("[Bajor", null));
		PLACES_MAP.put("Darien Wallace", IndividualLifeBoundaryPlacesDTO.of("Altair IV", null));
		PLACES_MAP.put("Seven of Nine", IndividualLifeBoundaryPlacesDTO.of("Tendara colony", null));
		PLACES_MAP.put("Beverly Crusher", IndividualLifeBoundaryPlacesDTO.of("Copernicus City, Luna", null));
		PLACES_MAP.put("Jean-Luc Picard", IndividualLifeBoundaryPlacesDTO.of("La Barre, France, Earth", null));
		PLACES_MAP.put("Philana", IndividualLifeBoundaryPlacesDTO.of("Sahndara", null));
		PLACES_MAP.put("Briam", IndividualLifeBoundaryPlacesDTO.of("Krios Prime", null));
		PLACES_MAP.put("Charles Tucker III", IndividualLifeBoundaryPlacesDTO.of("North America, Earth", null));
		PLACES_MAP.put("Thomas Raymond", IndividualLifeBoundaryPlacesDTO.of("Indianapolis, Indiana", null));
		PLACES_MAP.put("Kieran MacDuff", IndividualLifeBoundaryPlacesDTO.of("Gamma Canaris N", null));
		PLACES_MAP.put("John F. Kennedy", IndividualLifeBoundaryPlacesDTO.of(null, "Dallas, Texas"));
		PLACES_MAP.put("Clare Raymond", IndividualLifeBoundaryPlacesDTO.of("Indianapolis, Indiana, United States of America", null));
		PLACES_MAP.put("Lewis Zimmerman", IndividualLifeBoundaryPlacesDTO.of("Grover's Mill, New Jersey, Earth", null));
		PLACES_MAP.put("Leonard McCoy", IndividualLifeBoundaryPlacesDTO.of("Georgia, United States of America, Earth", null));
		PLACES_MAP.put("Janeway (Male Admiral)", IndividualLifeBoundaryPlacesDTO.of(null, "Tau Ceti Prime"));
		PLACES_MAP.put("Ishara Yar", IndividualLifeBoundaryPlacesDTO.of(TURKANA_VI, null));
		PLACES_MAP.put("Buck Bokai", IndividualLifeBoundaryPlacesDTO.of("Marina del Rey, California", null));
		PLACES_MAP.put("Kathryn Janeway", IndividualLifeBoundaryPlacesDTO.of("Bloomington, Indiana, Earth", null));
		PLACES_MAP.put("Montgomery Scott", IndividualLifeBoundaryPlacesDTO.of(SCOTLAND, null));
		PLACES_MAP.put("Leah Brahms", IndividualLifeBoundaryPlacesDTO.of("Damascus City, Alpha Delphi IX", null));
		PLACES_MAP.put("Hikaru Sulu", IndividualLifeBoundaryPlacesDTO.of("San Francisco, California, United States of America, Earth", null));
		PLACES_MAP.put("Leonard McCoy (alternate reality)", IndividualLifeBoundaryPlacesDTO.of("Georgia, North America, Earth", null));
		PLACES_MAP.put("Gary Mitchell", IndividualLifeBoundaryPlacesDTO.of("Eldman", DELTA_VEGA));
		PLACES_MAP.put("Christopher Pike (alternate reality)", IndividualLifeBoundaryPlacesDTO.of(MOJAVE, null));
		PLACES_MAP.put("James T. Kirk (alternate reality)", IndividualLifeBoundaryPlacesDTO.of("Medical shuttle 37, space", null));
		PLACES_MAP.put("Juliana O'Donnell", IndividualLifeBoundaryPlacesDTO.of(null, "Terlina III"));
		PLACES_MAP.put("Genghis Khan", IndividualLifeBoundaryPlacesDTO.of(EARTH, EARTH));
		PLACES_MAP.put("Elizabeth Dehner", IndividualLifeBoundaryPlacesDTO.of("Delman", DELTA_VEGA));
		PLACES_MAP.put("William T. Riker", IndividualLifeBoundaryPlacesDTO.of("Alaska, Earth", null));
		PLACES_MAP.put("Montgomery Scott (alternate reality)", IndividualLifeBoundaryPlacesDTO.of(SCOTLAND, null));
		PLACES_MAP.put("Ro Laren", IndividualLifeBoundaryPlacesDTO.of("Bajor", null));
		PLACES_MAP.put("Geordi La Forge", IndividualLifeBoundaryPlacesDTO.of("Mogadishu, Somalia, African Confederation, Earth", null));
	}

	@Override
	public FixedValueHolder<IndividualLifeBoundaryPlacesDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(PLACES_MAP.containsKey(key), PLACES_MAP.get(key));
	}

}
