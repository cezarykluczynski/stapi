package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class EpisodePerformancesMediaSections {

	public static final List<String> SKIPPABLE_PAGES = ImmutableList.copyOf(Lists.newArrayList(
			"lt.", "lieutenant",
			"counselor",
			"doctor", "dr.",
			"lieutenant commander", "lt. cmdr.",
			"commander", "cmdr.",
			"captain", "capt.",
			"stunt double",
			"stand-in",
			"ensign",
			"chief",
			"constable",
			"commodore",
			"photo double", "hand double",
			"crewman",
			"lieutenant junior grade", "lieutenant jg",
			"colonel",
			"gul",
			"admiral",
			"major",
			"kai"
	));

	static final String LINKS_AND_REFERENCES = "Links and references";
	static final String AS_SEPARATOR = " as ";
	static final String AS_END_LINE = " as";
	static final String FOR_SEPARATOR = " for ";
	static final String FOR_END_LINE = " for";
	static final String DASH_END_LINE = " –";
	static final String HYPEN_END_LINE = " -";
	static final String NDASH_END_LINE = " &ndash;";
	static final String OPEN_WIKITEXT_LINK = "[[";

	static final List<String> EPISODE_STARRING = Lists.newArrayList(
			"Guest stars",
			"Uncredited guest cast",
			"Starring the voices of",
			"Special Guest Stars",
			"Also starring",
			"Guest stars 2",
			"Featuring",
			"Special appearance by 2",
			"Also Starring",
			"Cast reappearances",
			"Special appearance by",
			"Co-stars",
			"Starring 2",
			"Special guest appearance by",
			"Cast and characters",
			"Actors appearing in the original Star Trek episode",
			"Special guest star",
			"Performers",
			"Uncredited",
			"Guest starring",
			"Co-starring",
			"Main cast",
			"Also starring the voices of",
			"Uncredited Co-Stars",
			"Starring",
			"Special guest stars in alphabetical order",
			"Also Starring 2",
			"CGI co-stars",
			"Guest star",
			"Additional characters",
			"Special Guest Star",
			"Special Apearance By",
			"Special Appearance By",
			"Co-Stars",
			"Guest Star",
			"Co-star",
			"Uncredited co-star",
			"Special guest stars",
			"Background Characters",
			"And guest starring",
			"Guest cast",
			"Uncredited co-stars appearing in the original Star Trek episode",
			"Cast",
			"And Special Guest Star",
			"Co-Star",
			"And special guest star",
			"Also starring 2",
			"Remastered extras",
			"Uncredited cast",
			"Characters",
			"Uncredited co-stars appearing in the original Star Trek episode \"The Cage\"",
			"Special guest appearances",
			"Uncredited archive footage appearances",
			"Special Guest Appearance by",
			"Guest Stars",
			"Uncredited Co-Star",
			"Special Guest Appearance By",
			"Uncredited co-stars",
			"Background characters",
			"Guest background characters",
			"Uncredited Co-stars"
	);

	static final List<String> EPISODE_STUNTS = Lists.newArrayList(
			"Stunt doubles",
			"Stunts",
			"Stunt double",
			// Duplicated section, decision was made to make it part of stunts
			"Stunt Doubles and Stand-ins",
			"Stunt doubles and stand-in",
			"Stunt doubles appearing in the original Star Trek episode",
			"Stunt doubles and stunt crew",
			"Stunt and body doubles",
			"Stunt Doubles",
			"Stunt Double",
			"Stunt performers",
			"Doubles"
	);

	static final List<String> EPISODE_STAND_INS = Lists.newArrayList(
			"Stand-ins and photo double",
			"Photo doubles",
			"Photo double",
			"Photo Double",
			"Stand-in",
			"Stand-ins",
			"Stand-ins and photo doubles",
			// Duplicated section, decision was made to make it part of stunts
			// "Stunt Doubles and Stand-ins",
			// "Stunt doubles and stand-in",
			"Stand-ins/Doubles",
			"Stand-ins and doubles",
			"Stand-ins and stunt doubles",
			"Stands-ins"
	);

	static final List<String> PERFORMANCES_SECTION = Lists.newArrayList();
	static final Map<String, PerformanceType> SECTIONS_TO_PERFORMANCE_TYPE_MAP = Maps.newLinkedHashMap();

	static {
		PERFORMANCES_SECTION.addAll(EPISODE_STARRING);
		PERFORMANCES_SECTION.addAll(EPISODE_STUNTS);
		PERFORMANCES_SECTION.addAll(EPISODE_STAND_INS);
		for (String section : EPISODE_STARRING) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.PERFORMANCE);
		}
		for (String section : EPISODE_STUNTS) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.STUNT);
		}
		for (String section : EPISODE_STAND_INS) {
			SECTIONS_TO_PERFORMANCE_TYPE_MAP.put(section, PerformanceType.STAND_IN);
		}
	}

}
