package com.cezarykluczynski.stapi.etl.util.constant;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class CategoryTitles {

	public static final List<String> COMPANIES = ImmutableList.of(
			CategoryTitle.COMPANIES,
			CategoryTitle.BROADCASTERS,
			CategoryTitle.STREAMING_SERVICES,
			CategoryTitle.COLLECTIBLE_COMPANIES,
			CategoryTitle.CONGLOMERATES,
			CategoryTitle.VISUAL_EFFECTS_COMPANIES,
			CategoryTitle.DIGITAL_VISUAL_EFFECTS_COMPANIES,
			CategoryTitle.DISTRIBUTORS,
			CategoryTitle.GAME_COMPANIES,
			CategoryTitle.FILM_EQUIPMENT_COMPANIES,
			CategoryTitle.MAKE_UP_EFFECTS_STUDIOS,
			CategoryTitle.MATTE_PAINTING_COMPANIES,
			CategoryTitle.MODEL_AND_MINIATURE_EFFECTS_COMPANIES,
			CategoryTitle.POST_PRODUCTION_COMPANIES,
			CategoryTitle.PRODUCTION_COMPANIES,
			CategoryTitle.PROP_COMPANIES,
			CategoryTitle.RECORD_LABELS,
			CategoryTitle.SPECIAL_EFFECTS_COMPANIES,
			CategoryTitle.TV_AND_FILM_PRODUCTION_COMPANIES,
			CategoryTitle.VIDEO_GAME_COMPANIES,
			CategoryTitle.PUBLISHERS,
			CategoryTitle.STAR_TREK_PUBLICATION_ART_STUDIOS
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
			CategoryTitle.FILMATION_PRODUCTION_STAFF,
			CategoryTitle.LINGUISTS,
			CategoryTitle.LOCATION_STAFF,
			CategoryTitle.MAKEUP_STAFF,
			CategoryTitle.MERCHANDISE_STAFF,
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
			CategoryTitle.STAR_TREK_COMIC_COVER_ARTISTS,
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
			CategoryTitle.DIS_EPISODES,
			CategoryTitle.PIC_EPISODES,
			CategoryTitle.LD_EPISODES,
			CategoryTitle.PRO_EPISODES,
			CategoryTitle.SNW_EPISODES
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
			CategoryTitle.LOCATIONS_ALTERNATE_REALITY,
			CategoryTitle.EARTH_LOCATIONS,
			CategoryTitle.EARTH_ROADS,
			CategoryTitle.EARTH_ESTABLISHMENTS,
			CategoryTitle.MEDICAL_ESTABLISHMENTS,
			CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED,
			CategoryTitle.WARDS,
			CategoryTitle.RESTAURANTS,
			CategoryTitle.ESTABLISHMENTS,
			CategoryTitle.SCHOOLS,
			CategoryTitle.STARFLEET_SCHOOLS,
			CategoryTitle.EARTH_SCHOOLS,
			CategoryTitle.ESTABLISHMENTS_RETCONNED,
			CategoryTitle.DS9_ESTABLISHMENTS,
			CategoryTitle.GEOGRAPHY,
			CategoryTitle.FICTIONAL_LOCATIONS,
			CategoryTitle.MYTHOLOGICAL_LOCATIONS,
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
			CategoryTitle.QONOS_LOCATIONS,
			CategoryTitle.QONOS_SETTLEMENTS,
			CategoryTitle.EARTH_GEOGRAPHY,
			CategoryTitle.LANDFORMS,
			CategoryTitle.RELIGIOUS_LOCATIONS,
			CategoryTitle.STRUCTURES,
			CategoryTitle.EARTH_STRUCTURES,
			CategoryTitle.BUILDING_INTERIORS,
			CategoryTitle.ROADS,
			CategoryTitle.SHIPYARDS,
			CategoryTitle.RESIDENCES
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
			CategoryTitle.ACTIVITY_BOOKS,
			CategoryTitle.ROLE_PLAYING_GAMES,
			CategoryTitle.STAR_TREK_LITERATURE,
			CategoryTitle.PRODUCTION_USE_DOCUMENTS,
			CategoryTitle.AUDIO_DRAMAS,
			CategoryTitle.UNAUTHORIZED_PUBLICATIONS
	);

	public static final List<String> BOOK_SERIES = ImmutableList.of(
			CategoryTitle.NOVEL_SERIES,
			CategoryTitle.E_BOOK_SERIES
	);

	public static final List<String> ASTRONOMICAL_OBJECTS = ImmutableList.of(
			CategoryTitle.ASTEROIDS,
			CategoryTitle.ASTEROID_BELTS,
			CategoryTitle.BORG_SPATIAL_DESIGNATIONS,
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

	public static final List<String> SPECIES = ImmutableList.of(
			CategoryTitle.SPECIES,
			CategoryTitle.UNNAMED_SPECIES,
			CategoryTitle.NON_CORPOREAL_SPECIES,
			CategoryTitle.TELEPATHIC_SPECIES
	);

	public static final List<String> TITLES = ImmutableList.of(
			CategoryTitle.TITLES,
			CategoryTitle.MILITARY_RANKS,
			CategoryTitle.RELIGIOUS_TITLES,
			CategoryTitle.EDUCATION_TITLES
	);

	public static final List<String> OCCUPATIONS = ImmutableList.of(
			CategoryTitle.OCCUPATIONS,
			CategoryTitle.ARTS_OCCUPATIONS,
			CategoryTitle.COMMUNICATION_OCCUPATIONS,
			CategoryTitle.ECONOMIC_OCCUPATIONS,
			CategoryTitle.EDUCATION_OCCUPATIONS,
			CategoryTitle.ENTERTAINMENT_OCCUPATIONS,
			CategoryTitle.ILLEGAL_OCCUPATIONS,
			CategoryTitle.LEGAL_OCCUPATIONS,
			CategoryTitle.MEDICAL_OCCUPATIONS,
			CategoryTitle.SCIENTIFIC_OCCUPATIONS,
			CategoryTitle.SPORTS_OCCUPATIONS,
			CategoryTitle.VICTUAL_OCCUPATIONS
	);

	public static final List<String> FOODS = ImmutableList.of(
			CategoryTitle.FOODS,
			CategoryTitle.EARTH_FOODS,
			CategoryTitle.DESSERTS,
			CategoryTitle.FRUITS,
			CategoryTitle.HERBS_AND_SPICES,
			CategoryTitle.EARTH_HERBS_AND_SPICES,
			CategoryTitle.SAUCES,
			CategoryTitle.SOUPS,
			CategoryTitle.BEVERAGES,
			CategoryTitle.ALCOHOLIC_BEVERAGES,
			CategoryTitle.EARTH_BEVERAGES,
			CategoryTitle.JUICES,
			CategoryTitle.TEA
	);

	public static final List<String> VIDEO_RELEASES = ImmutableList.of(
			CategoryTitle.VIDEO_RELEASES,
			CategoryTitle._4K_ULTRA_HD_BLU_RAYS,
			CategoryTitle.BETAMAX_RELEASES,
			CategoryTitle.BLU_RAY_DISCS,
			CategoryTitle.CEDS,
			CategoryTitle.DIGITAL_RELEASES,
			CategoryTitle.DVDS,
			CategoryTitle.LASER_DISCS,
			CategoryTitle.SUPER_8S,
			CategoryTitle.VCDS,
			CategoryTitle.VHDS,
			CategoryTitle.VHS_RELEASES,
			CategoryTitle.UK_VHS_RELEASES,
			CategoryTitle.US_VHS_RELEASES,
			CategoryTitle.VIDEO_8S
	);

	public static final List<String> WEAPONS = ImmutableList.of(
			CategoryTitle.WEAPONS,
			CategoryTitle.DIRECTED_ENERGY_WEAPONS,
			CategoryTitle.EXPLOSIVE_WEAPONS,
			CategoryTitle.FICTIONAL_WEAPONS,
			CategoryTitle.HAND_HELD_WEAPONS,
			CategoryTitle.PROJECTILE_WEAPONS
	);

	public static final List<String> SPACECRAFT_TYPES = ImmutableList.of(
			CategoryTitle.SPACECRAFT_CLASSIFICATIONS,
			CategoryTitle.SPACECRAFT_CLASSIFICATIONS_ALTERNATE_REALITY,
			CategoryTitle.STATION_TYPES
	);

	public static final List<String> SPACECRAFT_CLASSES = ImmutableList.of(
			CategoryTitle.SPACECRAFT_CLASSES,
			CategoryTitle.SPACECRAFT_CLASSES_ALTERNATE_REALITY,
			CategoryTitle.STARSHIP_CLASSES_ALTERNATE_REALITY,
			CategoryTitle.SPACECRAFT_CLASSES_MIRROR,
			CategoryTitle.STARSHIP_CLASSES_MIRROR,
			CategoryTitle.EARTH_SPACECRAFT_CLASSES,
			CategoryTitle.EARTH_SPACECRAFT_CLASSES_RETCONNED,
			CategoryTitle.EARTH_STARSHIP_CLASSES,
			CategoryTitle.CONFEDERATION_STARSHIP_CLASSES,
			CategoryTitle.ESCAPE_POD_CLASSES,
			CategoryTitle.SHUTTLE_CLASSES,
			CategoryTitle.FEDERATION_SHUTTLE_CLASSES,
			CategoryTitle.STARSHIP_CLASSES,
			CategoryTitle.FEDERATION_STARSHIP_CLASSES,
			CategoryTitle.FEDERATION_STARSHIP_CLASSES_ALTERNATE_REALITY,
			CategoryTitle.FEDERATION_STARSHIP_CLASSES_RETCONNED
	);


	public static final List<String> SPACECRAFTS = ImmutableList.of(
			CategoryTitle.SPACECRAFT,
			CategoryTitle.SPACECRAFT_ALTERNATE_REALITY,
			CategoryTitle.SHUTTLES_ALTERNATE_REALITY,
			CategoryTitle.STARSHIPS_ALTERNATE_REALITY,
			CategoryTitle.SPACECRAFT_MIRROR,
			CategoryTitle.STARSHIPS_MIRROR,
			CategoryTitle.EARTH_SPACECRAFT,
			CategoryTitle.EARTH_STARSHIPS,
			CategoryTitle.CONFEDERATION_STARSHIPS,
			CategoryTitle.EARTH_PROBES,
			CategoryTitle.EARTH_PROBES_RETCONNED,
			CategoryTitle.EARTH_SHUTTLES,
			CategoryTitle.ESCAPE_PODS,
			CategoryTitle.FERENGI_SPACECRAFT,
			CategoryTitle.FERENGI_STARSHIPS,
			CategoryTitle.PROBES,
			CategoryTitle.SHUTTLES,
			CategoryTitle.EARTH_SHUTTLES,
			CategoryTitle.FEDERATION_SHUTTLES,
			CategoryTitle.STARSHIPS,
			CategoryTitle.STARSHIPS_ALTERNATE_REALITY,
			CategoryTitle.ANDORIAN_STARSHIPS,
			CategoryTitle.BORG_STARSHIPS,
			CategoryTitle.CARDASSIAN_STARSHIPS,
			CategoryTitle.DOMINION_STARSHIPS,
			CategoryTitle.FEDERATION_STARSHIPS,
			CategoryTitle.FEDERATION_STARSHIPS_ALTERNATE_REALITY,
			CategoryTitle.FEDERATION_STARSHIPS_RETCONNED,
			CategoryTitle.FICTIONAL_STARSHIPS,
			CategoryTitle.ROMULAN_STARSHIPS,
			CategoryTitle.VULCAN_STARSHIPS,
			CategoryTitle.SPACE_STATIONS,
			CategoryTitle.OUTPOSTS,
			CategoryTitle.STARBASES,
			CategoryTitle.STARBASES_RETCONNED
	);

	public static final List<String> MATERIALS = ImmutableList.of(
			CategoryTitle.MATERIALS,
			CategoryTitle.EXPLOSIVES,
			CategoryTitle.GEMSTONES,
			CategoryTitle.CHEMICAL_COMPOUNDS,
			CategoryTitle.BIOCHEMICAL_COMPOUNDS,
			CategoryTitle.DRUGS,
			CategoryTitle.SEDATIVES,
			CategoryTitle.EXPLOSIVE_WEAPONS,
			CategoryTitle.POISONOUS_SUBSTANCES,
			CategoryTitle.FABRICS
	);

	public static final List<String> CONFLICTS = ImmutableList.of(
			CategoryTitle.CONFLICTS,
			CategoryTitle.EARTH_CONFLICTS,
			CategoryTitle.EARTH_CONFLICTS_RETCONNED,
			CategoryTitle.CONFLICTS_ALTERNATE_REALITY
	);

	public static final List<String> ANIMALS = ImmutableList.of(
			CategoryTitle.ANIMALS,
			CategoryTitle.EARTH_ANIMALS,
			CategoryTitle.EARTH_ANIMALS_RETCONNED,
			CategoryTitle.EARTH_INSECTS,
			CategoryTitle.AVIANS,
			CategoryTitle.CANINES,
			CategoryTitle.FELINES
	);

	public static final List<String> TECHNOLOGY = ImmutableList.of(
			CategoryTitle.TECHNOLOGY,
			CategoryTitle.BORG_TECHNOLOGY,
			CategoryTitle.BORG_COMPONENTS,
			CategoryTitle.COMMUNICATIONS_TECHNOLOGY,
			CategoryTitle.COMMUNICATIONS_TECHNOLOGY_RETCONNED,
			CategoryTitle.COMPUTER_TECHNOLOGY,
			CategoryTitle.COMPUTER_PROGRAMMING,
			CategoryTitle.SUBROUTINES,
			CategoryTitle.DATABASES,
			CategoryTitle.ENERGY_TECHNOLOGY,
			CategoryTitle.ENERGY_TECHNOLOGY_RETCONNED,
			CategoryTitle.FICTIONAL_TECHNOLOGY,
			CategoryTitle.HOLOGRAPHIC_TECHNOLOGY,
			CategoryTitle.IDENTIFICATION_TECHNOLOGY,
			CategoryTitle.LIFE_SUPPORT_TECHNOLOGY,
			CategoryTitle.SENSOR_TECHNOLOGY,
			CategoryTitle.SENSOR_TECHNOLOGY_RETCONNED,
			CategoryTitle.SHIELD_TECHNOLOGY,
			CategoryTitle.SHIELD_TECHNOLOGY_RETCONNED,
			CategoryTitle.TOOLS,
			CategoryTitle.CULINARY_TOOLS,
			CategoryTitle.ENGINEERING_TOOLS,
			CategoryTitle.HOUSEHOLD_TOOLS,
			CategoryTitle.MEDICAL_EQUIPMENT,
			CategoryTitle.TRANSPORTER_TECHNOLOGY,
			CategoryTitle.TRANSPORTATION_TECHNOLOGY,
			CategoryTitle.SECURITY_TECHNOLOGY,
			CategoryTitle.SPACECRAFT_COMPONENTS,
			CategoryTitle.SPACECRAFT_COMPONENTS_RETCONNED,
			CategoryTitle.PROPULSION_TECHNOLOGY,
			CategoryTitle.PROPULSION_TECHNOLOGY_RETCONNED,
			CategoryTitle.WARP,
			CategoryTitle.TRANSWARP,
			CategoryTitle.TIME_TRAVEL_TECHNOLOGY,
			CategoryTitle.MILITARY_TECHNOLOGY,
			CategoryTitle.VICTUAL_TECHNOLOGY,
			CategoryTitle.WEAPON_COMPONENTS,
			CategoryTitle.ARTIFICIAL_LIFEFORM_COMPONENTS
	);

}
