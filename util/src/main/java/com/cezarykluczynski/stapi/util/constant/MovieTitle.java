package com.cezarykluczynski.stapi.util.constant;

import com.google.common.collect.Maps;

import java.util.Map;

public class MovieTitle {

	public static final Map<Long, String> FILM_TEMPLATE_TO_MOVIE_TITLE_MAP = Maps.newLinkedHashMap();

	static {
		// source: https://memory-alpha.fandom.com/wiki/Template:Film
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(1L, "Star Trek: The Motion Picture");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(2L, "Star Trek II: The Wrath of Khan");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(3L, "Star Trek III: The Search for Spock");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(4L, "Star Trek IV: The Voyage Home");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(5L, "Star Trek V: The Final Frontier");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(6L, "Star Trek VI: The Undiscovered Country");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(7L, "Star Trek Generations");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(8L, "Star Trek: First Contact");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(9L, "Star Trek: Insurrection");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(10L, "Star Trek Nemesis");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(11L, "Star Trek");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(12L, "Star Trek Into Darkness");
		FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(13L, "Star Trek Beyond");
		// FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.put(14L, "Star Trek XIV"); // not yet released
	}

	public static String movieAtIndex(int index) {
		return FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get((long) index);
	}

}
