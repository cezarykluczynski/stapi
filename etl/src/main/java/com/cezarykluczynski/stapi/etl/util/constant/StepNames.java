package com.cezarykluczynski.stapi.etl.util.constant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class StepNames {

	public static final Map<String, List<String>> JOB_STEPS = Maps.newHashMap();

	static {
		JOB_STEPS.put(JobName.JOB_CREATE, Lists.newArrayList(
				StepName.CREATE_COMPANIES,
				StepName.CREATE_SERIES,
				StepName.CREATE_SEASONS,
				StepName.CREATE_PERFORMERS,
				StepName.CREATE_STAFF,
				StepName.CREATE_ASTRONOMICAL_OBJECTS,
				StepName.CREATE_SPECIES,
				StepName.CREATE_ORGANIZATIONS,
				StepName.CREATE_TITLES,
				StepName.CREATE_CHARACTERS,
				StepName.LINK_CHARACTERS,
				StepName.CREATE_EPISODES,
				StepName.CREATE_MOVIES,
				StepName.LINK_ASTRONOMICAL_OBJECTS,
				StepName.CREATE_COMIC_SERIES,
				StepName.LINK_COMIC_SERIES,
				StepName.CREATE_COMICS,
				StepName.CREATE_COMIC_STRIPS,
				StepName.CREATE_COMIC_COLLECTIONS,
				StepName.CREATE_FOODS,
				StepName.CREATE_LOCATIONS,
				StepName.CREATE_BOOK_SERIES,
				StepName.LINK_BOOK_SERIES,
				StepName.CREATE_BOOKS,
				StepName.CREATE_BOOK_COLLECTIONS,
				StepName.CREATE_MAGAZINE_SERIES,
				StepName.CREATE_MAGAZINES,
				StepName.CREATE_LITERATURE,
				StepName.CREATE_VIDEO_RELEASES,
				StepName.CREATE_TRADING_CARDS,
				StepName.CREATE_VIDEO_GAMES,
				StepName.CREATE_SOUNDTRACKS,
				StepName.CREATE_WEAPONS,
				StepName.CREATE_SPACECRAFT_TYPES,
				StepName.CREATE_SPACECRAFT_CLASSES,
				StepName.CREATE_SPACECRAFTS,
				StepName.CREATE_MATERIALS,
				StepName.CREATE_CONFLICTS,
				StepName.CREATE_ANIMALS,
				StepName.CREATE_ELEMENTS,
				StepName.CREATE_MEDICAL_CONDITIONS,
				StepName.CREATE_TECHNOLOGY,
				StepName.CREATE_OCCUPATIONS
		));
	}

}
