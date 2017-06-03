package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class EpisodeTemplateStardateYearFixedValueProvider implements FixedValueProvider<String, StardateYearDTO> {

	private static final Map<String, StardateYearDTO> TITLE_TO_STARDATE_YEAR_MAP = Maps.newHashMap();

	static {
		TITLE_TO_STARDATE_YEAR_MAP.put("Tomorrow is Yesterday", StardateYearDTO.of(3113.2F, 3113.2F, 1969, 2267));
		TITLE_TO_STARDATE_YEAR_MAP.put("That Which Survives", StardateYearDTO.of(3113.2F, 3113.2F, 1969, 2267));
		TITLE_TO_STARDATE_YEAR_MAP.put("Albatross", StardateYearDTO.of(5275.6F, 5276.8F, 2270, 2270));
		TITLE_TO_STARDATE_YEAR_MAP.put("Beyond the Farthest Star", StardateYearDTO.of(5221.3F, 5221.8F, 2269, 2269));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Time Trap", StardateYearDTO.of(5267.2F, 5267.6F, 2269, 2269));
		TITLE_TO_STARDATE_YEAR_MAP.put("All Good Things...", StardateYearDTO.of(47988.0F, 47988.0F, null, 2395));
		TITLE_TO_STARDATE_YEAR_MAP.put("Azati Prime", StardateYearDTO.of(null, null, 2154, null));
		TITLE_TO_STARDATE_YEAR_MAP.put("Divergence", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shadows of P'Jem", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Aenar", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Conspiracy", StardateYearDTO.of(41775.5F, 41780.2F, 2364, 2364));
		TITLE_TO_STARDATE_YEAR_MAP.put("Bar Association", StardateYearDTO.of(49565F, 49565F, 2372, 2372));
		TITLE_TO_STARDATE_YEAR_MAP.put("Facets", StardateYearDTO.of(48959F, 48959F, 2371, 2371));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shakaar", StardateYearDTO.of(48764F, 48764F, 2371, 2371));
		TITLE_TO_STARDATE_YEAR_MAP.put("What You Leave Behind", StardateYearDTO.of(52902F, 52902F, 2375, 2376));
		TITLE_TO_STARDATE_YEAR_MAP.put("Tapestry", StardateYearDTO.of(null, null, 2327, 2369));
		TITLE_TO_STARDATE_YEAR_MAP.put("Time's Arrow", StardateYearDTO.of(45959.1F, 45959.1F, 1893, 2368));
		TITLE_TO_STARDATE_YEAR_MAP.put("Time's Arrow, Part II", StardateYearDTO.of(46001.3F, 46001.3F, 1893, 2369));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Assignment", StardateYearDTO.of(null, null, 2373, 2373));
		TITLE_TO_STARDATE_YEAR_MAP.put("Far Beyond the Stars", StardateYearDTO.of(null, null, 1953, 2374));
		TITLE_TO_STARDATE_YEAR_MAP.put("A Man Alone", StardateYearDTO.of(46384F, 46384F, 2369, 2369));
		TITLE_TO_STARDATE_YEAR_MAP.put("Past Tense, Part I", StardateYearDTO.of(48481.2F, 48481.2F, 2024, 2371));
		TITLE_TO_STARDATE_YEAR_MAP.put("Past Tense, Part II", StardateYearDTO.of(null, null, 1930, 2371));
		TITLE_TO_STARDATE_YEAR_MAP.put("Time's Orphan", StardateYearDTO.of(null, null, null, 2374));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Visitor", StardateYearDTO.of(null, null, 2372, 2450));
		TITLE_TO_STARDATE_YEAR_MAP.put("11:59", StardateYearDTO.of(52840F, 52840F, 2000, 2375));
		TITLE_TO_STARDATE_YEAR_MAP.put("Before and After", StardateYearDTO.of(null, null, 2369, 2379));
		TITLE_TO_STARDATE_YEAR_MAP.put("Cold Fire", StardateYearDTO.of(49164.8F, 49164.8F, 2372, 2372));
		TITLE_TO_STARDATE_YEAR_MAP.put("Course: Oblivion", StardateYearDTO.of(52586.3F, 52597.4F, 2375, 2375));
		TITLE_TO_STARDATE_YEAR_MAP.put("Fury", StardateYearDTO.of(null, null, 2371, 2376));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Haunting of Deck Twelve", StardateYearDTO.of(null, null, 2376, 2377));
		TITLE_TO_STARDATE_YEAR_MAP.put("Homestead", StardateYearDTO.of(54868.6F, 54868.6F, 2378, 2378));
		TITLE_TO_STARDATE_YEAR_MAP.put("Learning Curve", StardateYearDTO.of(48846.5F, 48846.5F, 2371, 2371));
		TITLE_TO_STARDATE_YEAR_MAP.put("Living Witness", StardateYearDTO.of(null, null, 3074, 3074));
		TITLE_TO_STARDATE_YEAR_MAP.put("One Small Step", StardateYearDTO.of(53292.7F, 53292.7F, 2032, 2376));
		TITLE_TO_STARDATE_YEAR_MAP.put("Parturition", StardateYearDTO.of(49068.5F, 49068.5F, 2372, 2372));
		TITLE_TO_STARDATE_YEAR_MAP.put("Relativity", StardateYearDTO.of(52861.274F, 52861.274F, 2371, null));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shattered", StardateYearDTO.of(null, null, 2371, 2394));
		TITLE_TO_STARDATE_YEAR_MAP.put("Tattoo", StardateYearDTO.of(null, null, 2344, 2372));
		TITLE_TO_STARDATE_YEAR_MAP.put("Year of Hell", StardateYearDTO.of(51268.4F, 51268.4F, 2374, 2374));
		TITLE_TO_STARDATE_YEAR_MAP.put("Year of Hell, Part II", StardateYearDTO.of(51425.4F, 51425.4F, 2374, 2374));
		TITLE_TO_STARDATE_YEAR_MAP.put("Affliction", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Augments", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Babel One", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Borderland", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Bound", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Bounty", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Broken Bow", StardateYearDTO.of(null, null, 2121, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("Carbon Creek", StardateYearDTO.of(null, null, 1957, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Carpenter Street", StardateYearDTO.of(null, null, 2004, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Catwalk", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Civilization", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("Cold Front", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Council", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Countdown", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Dead Stop", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Demons", StardateYearDTO.of(null, null, 2155, 2155));
		TITLE_TO_STARDATE_YEAR_MAP.put("Desert Crossing", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Expanse", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Fallen Hero", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Fight or Flight", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("First Flight", StardateYearDTO.of(null, null, 2143, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Harbinger", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Gravity", StardateYearDTO.of(52438.9F, 52438.9F, null, null));
		TITLE_TO_STARDATE_YEAR_MAP.put("Lineage", StardateYearDTO.of(54452.6F, 54452.6F, null, 2377));
		TITLE_TO_STARDATE_YEAR_MAP.put("Hatchery", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Horizon", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("In a Mirror, Darkly", StardateYearDTO.of(null, null, 2063, 2155));
		TITLE_TO_STARDATE_YEAR_MAP.put("In a Mirror, Darkly, Part II", StardateYearDTO.of(null, null, 2155, 2155));
		TITLE_TO_STARDATE_YEAR_MAP.put("Precious Cargo", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Proving Ground", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Regeneration", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shockwave", StardateYearDTO.of(null, null, 2151, 3052));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shockwave, Part II", StardateYearDTO.of(null, null, 2151, null));
		TITLE_TO_STARDATE_YEAR_MAP.put("Shuttlepod One", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("Silent Enemy", StardateYearDTO.of(null, null, 2151, 2151));
		TITLE_TO_STARDATE_YEAR_MAP.put("Singularity", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("Stratagem", StardateYearDTO.of(null, null, 2153, 2153));
		TITLE_TO_STARDATE_YEAR_MAP.put("Terra Prime", StardateYearDTO.of(null, null, 2155, 2155));
		TITLE_TO_STARDATE_YEAR_MAP.put("Two Days and Two Nights", StardateYearDTO.of(null, null, 2152, 2152));
		TITLE_TO_STARDATE_YEAR_MAP.put("United", StardateYearDTO.of(null, null, 2154, 2154));
		TITLE_TO_STARDATE_YEAR_MAP.put("Zero Hour", StardateYearDTO.of(null, null, 1944, 2161));
		TITLE_TO_STARDATE_YEAR_MAP.put("The Andorian Incident", StardateYearDTO.of(null, null, 2151, 2151));
	}

	@Override
	public FixedValueHolder<StardateYearDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_STARDATE_YEAR_MAP.containsKey(key), TITLE_TO_STARDATE_YEAR_MAP.get(key));
	}

}
