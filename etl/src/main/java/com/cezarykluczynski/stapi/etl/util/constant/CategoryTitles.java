package com.cezarykluczynski.stapi.etl.util.constant;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class CategoryTitles {

	public static final List<String> PERFORMERS = ImmutableList.of(
			CategoryTitle.PERFORMERS,
			CategoryTitle.ANIMAL_PERFORMERS,
			CategoryTitle.DIS_PERFORMERS,
			CategoryTitle.DS9_PERFORMERS,
			CategoryTitle.ENT_PERFORMERS,
			CategoryTitle.FILM_PERFORMERS,
			CategoryTitle.STAND_INS,
			CategoryTitle.STUNT_PERFORMERS,
			CategoryTitle.TAS_PERFORMERS,
			CategoryTitle.TNG_PERFORMERS,
			CategoryTitle.TOS_PERFORMERS,
			CategoryTitle.TOS_REMASTERED_PERFORMERS,
			CategoryTitle.VIDEO_GAME_PERFORMERS,
			CategoryTitle.VOICE_PERFORMERS,
			CategoryTitle.VOY_PERFORMERS
	);

	public static final List<String> STAFF = ImmutableList.of(
			CategoryTitle.ART_DEPARTMENT,
			CategoryTitle.ART_DIRECTORS,
			CategoryTitle.PRODUCTION_DESIGNERS,
			CategoryTitle.CAMERA_AND_ELECTRICAL_DEPARTMENT,
			CategoryTitle.CINEMATOGRAPHERS,
			CategoryTitle.CASTING_DEPARTMENT,
			CategoryTitle.COSTUME_DEPARTMENT,
			CategoryTitle.COSTUME_DESIGNERS,
			CategoryTitle.DIRECTORS,
			CategoryTitle.ASSISTANT_AND_SECOND_UNIT_DIRECTORS,
			CategoryTitle.EXHIBIT_AND_ATTRACTION_STAFF,
			CategoryTitle.FILM_EDITORS,
			CategoryTitle.LINGUISTS,
			CategoryTitle.LOCATION_STAFF,
			CategoryTitle.MAKEUP_STAFF,
			CategoryTitle.MUSIC_DEPARTMENT,
			CategoryTitle.COMPOSERS,
			CategoryTitle.PERSONAL_ASSISTANTS,
			CategoryTitle.PRODUCERS,
			CategoryTitle.PRODUCTION_ASSOCIATES,
			CategoryTitle.PRODUCTION_STAFF,
			CategoryTitle.PUBLICATION_STAFF,
			CategoryTitle.SCIENCE_CONSULTANTS,
			CategoryTitle.SOUND_DEPARTMENT,
			CategoryTitle.SPECIAL_AND_VISUAL_EFFECTS_STAFF,
			CategoryTitle.STAR_TREK_AUTHORS,
			CategoryTitle.STAR_TREK_AUDIO_AUTHORS,
			CategoryTitle.STAR_TREK_CALENDAR_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_AUTHORS,
			CategoryTitle.STAR_TREK_COMIC_COLOR_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_INTERIOR_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_INK_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_PENCIL_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_LETTER_ARTISTS,
			CategoryTitle.STAR_TREK_COMIC_STRIP_ARTISTS,
			CategoryTitle.STAR_TREK_GAME_ARTISTS,
			CategoryTitle.STAR_TREK_GAME_AUTHORS,
			CategoryTitle.STAR_TREK_NOVEL_ARTISTS,
			CategoryTitle.STAR_TREK_NOVEL_AUTHORS,
			CategoryTitle.STAR_TREK_REFERENCE_ARTISTS,
			CategoryTitle.STAR_TREK_REFERENCE_AUTHORS,
			CategoryTitle.STAR_TREK_PUBLICATION_ARTISTS,
			CategoryTitle.STAR_TREK_PUBLICATION_DESIGNERS,
			CategoryTitle.STAR_TREK_PUBLICATION_EDITORS,
			CategoryTitle.STAR_TREK_PUBLICITY_ARTISTS,
			CategoryTitle.CBS_DIGITAL_STAFF,
			CategoryTitle.ILM_PRODUCTION_STAFF,
			CategoryTitle.SPECIAL_FEATURES_STAFF,
			CategoryTitle.STORY_EDITORS,
			CategoryTitle.STUDIO_EXECUTIVES,
			CategoryTitle.STUNT_DEPARTMENT,
			CategoryTitle.TRANSPORTATION_DEPARTMENT,
			CategoryTitle.VIDEO_GAME_PRODUCTION_STAFF,
			CategoryTitle.WRITERS
	);

	public static final List<String> EPISODES = ImmutableList.of(
			CategoryTitle.TOS_EPISODES,
			CategoryTitle.TAS_EPISODES,
			CategoryTitle.TNG_EPISODES,
			CategoryTitle.DS9_EPISODES,
			CategoryTitle.VOY_EPISODES,
			CategoryTitle.ENT_EPISODES,
			CategoryTitle.DIS_EPISODES
	);

	public static final List<String> ORGANIZATIONS = ImmutableList.of(
			CategoryTitle.AGENCIES,
			CategoryTitle.BAJORAN_AGENCIES,
			CategoryTitle.CARDASSIAN_AGENCIES,
			CategoryTitle.EARTH_AGENCIES,
			CategoryTitle.FEDERATION_AGENCIES,
			CategoryTitle.FERENGI_AGENCIES,
			CategoryTitle.KLINGON_AGENCIES,
			CategoryTitle.LAW_ENFORCEMENT_AGENCIES,
			CategoryTitle.PRISONS_AND_PENAL_COLONIES,
			CategoryTitle.ROMULAN_AGENCIES,
			CategoryTitle.VULCAN_AGENCIES,
			CategoryTitle.EARTH_ORGANIZATIONS,
			CategoryTitle.EARTH_INTERGOVERNMENTAL_ORGANIZATIONS,
			CategoryTitle.EARTH_MILITARY_ORGANIZATIONS,
			CategoryTitle.GOVERNMENTS,
			CategoryTitle.INTERGOVERNMENTAL_ORGANIZATIONS,
			CategoryTitle.RESEARCH_ORGANIZATIONS,
			CategoryTitle.SPORTS_ORGANIZATIONS,
			CategoryTitle.MEDICAL_ORGANIZATIONS,
			CategoryTitle.MILITARY_ORGANIZATIONS,
			CategoryTitle.MILITARY_UNITS
	);

	public static final List<String> LOCATIONS = ImmutableList.of(
			CategoryTitle.LOCATIONS,
			CategoryTitle.EARTH_LOCATIONS,
			CategoryTitle.EARTH_LANDMARKS,
			CategoryTitle.EARTH_ROADS,
			CategoryTitle.EARTH_ESTABLISHMENTS,
			CategoryTitle.MEDICAL_ESTABLISHMENTS,
			CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED,
			CategoryTitle.WARDS,
			CategoryTitle.ESTABLISHMENTS,
			CategoryTitle.SCHOOLS,
			CategoryTitle.STARFLEET_SCHOOLS,
			CategoryTitle.EARTH_SCHOOLS,
			CategoryTitle.ESTABLISHMENTS_RETCONNED,
			CategoryTitle.DS9_ESTABLISHMENTS,
			CategoryTitle.GEOGRAPHY,
			CategoryTitle.FICTIONAL_LOCATIONS,
			CategoryTitle.BODIES_OF_WATER,
			CategoryTitle.EARTH_BODIES_OF_WATER,
			CategoryTitle.COUNTRIES,
			CategoryTitle.EARTH_COUNTRIES,
			CategoryTitle.SUBNATIONAL_ENTITIES,
			CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED,
			CategoryTitle.EARTH_SUBNATIONAL_ENTITIES,
			CategoryTitle.SETTLEMENTS,
			CategoryTitle.BAJORAN_SETTLEMENTS,
			CategoryTitle.COLONIES,
			CategoryTitle.SETTLEMENTS_RETCONNED,
			CategoryTitle.EARTH_SETTLEMENTS,
			CategoryTitle.US_SETTLEMENTS,
			CategoryTitle.US_SETTLEMENTS_RETCONNED,
			CategoryTitle.EARTH_GEOGRAPHY,
			CategoryTitle.LANDFORMS,
			CategoryTitle.RELIGIOUS_LOCATIONS,
			CategoryTitle.STRUCTURES,
			CategoryTitle.BUILDING_INTERIORS,
			CategoryTitle.LANDMARKS,
			CategoryTitle.ROADS,
			CategoryTitle.SHIPYARDS
	);

	public static final List<String> LISTS = ImmutableList.of(
			CategoryTitle.PRODUCTION_LISTS,
			CategoryTitle.LISTS,
			CategoryTitle.PERSONNEL_LISTS
	);

	public static final List<String> BOOKS = ImmutableList.of(
			CategoryTitle.NOVELS,
			CategoryTitle.E_BOOKS,
			CategoryTitle.AUDIOBOOKS,
			CategoryTitle.NOVELIZATIONS,
			CategoryTitle.ANTHOLOGIES,
			CategoryTitle.REFERENCE_BOOKS,
			CategoryTitle.BIOGRAPHY_BOOKS,
			CategoryTitle.ROLE_PLAYING_GAMES
	);

	public static final List<String> BOOK_SERIES = ImmutableList.of(
			CategoryTitle.NOVEL_SERIES,
			CategoryTitle.E_BOOK_SERIES
	);

	public static final List<String> ASTRONOMICAL_OBJECTS = ImmutableList.of(
			CategoryTitle.ASTEROIDS,
			CategoryTitle.ASTEROID_BELTS,
			CategoryTitle.CLUSTERS,
			CategoryTitle.COMETS,
			CategoryTitle.CONSTELLATIONS,
			CategoryTitle.GALAXIES,
			CategoryTitle.HOMEWORLDS,
			CategoryTitle.MOONS,
			CategoryTitle.NEBULAE,
			CategoryTitle.NEBULAE_RETCONNED,
			CategoryTitle.PLANETOIDS,
			CategoryTitle.PLANETS,
			CategoryTitle.PLANETS_RETCONNED,
			CategoryTitle.UNNAMED_PLANETS,
			CategoryTitle.REGIONS,
			CategoryTitle.QUASARS,
			CategoryTitle.SECTORS,
			CategoryTitle.STAR_SYSTEMS,
			CategoryTitle.STARS
	);

}
