package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.MovieTitle;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class VideoTemplateContentsFixedValueEnricher implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private static final List<String> EMPTY_CONTENTS_TITLES = List.of(
			"Jonathan Ross' Essential Guide To Star Trek: First Contact",
			"Journey's End: The Saga of Star Trek: The Next Generation",
			"Journey's End: The Saga of Star Trek: The Next Generation (VHS)",
			"Sense of Scale",
			"Star Trek: Discovery (digital)", // stub on MA
			"Star Trek: The Next Generation - Borg Box", // then TNG series added
			"Star Trek: The Next Generation Motion Picture Collection (Blu-ray)", // then add all movies added
			"Star Trek: The Next Generation Motion Picture Collection (DVD)", // then add all movies added
			"Star Trek: The Original Series (Betamax)", // then add all TOS seasons
			"Star Trek: The Original Series (VHS)", // then add all TOS seasons
			"Star Trek: The Original Series - Origins", // then add TOS series
			"Star Trek: The Real Story",
			"Star Wars vs. Star Trek: The Rivalry Continues",
			"The Trek Not Taken"
	);

	private static final Map<String, SeriesList> SERIES_SETS_TO_RANGES = ImmutableMap.<String, SeriesList>builder()
			.put("Star Trek - 30th Anniversary Trial Pack", SeriesList.ofAbbreviations("TOS", "TNG", "DS9", "VOY"))
			.put("Star Trek - The Seven of Nine Collection", SeriesList.ofAbbreviations("VOY"))
			.put("Star Trek: The Next Generation - Borg Box", SeriesList.ofAbbreviations("TNG")) // first cleared
			.put("Star Trek: The Original Series (Betamax)", SeriesList.ofAbbreviations("TOS")) // first cleared
			.put("Star Trek: The Original Series (VHS)", SeriesList.ofAbbreviations("TOS")) // first cleared
			.put("Star Trek: The Original Series - Origins", SeriesList.ofAbbreviations("TOS")) // first cleared
			.build();

	private static final Map<String, Seasons> SEASONS_SETS_TO_RANGES = ImmutableMap.<String, Seasons>builder()
			.put("Star Trek: The Animated Series (Betamax)", Seasons.allOfSeries("TAS"))
			.put("Star Trek: The Animated Series (LaserDisc)", Seasons.allOfSeries("TAS"))
			.put("Star Trek: The Animated Series (VHS)", Seasons.allOfSeries("TAS"))
			.put("Star Trek: The Animated Series (digital)", Seasons.allOfSeries("TAS"))
			.put("Star Trek: Deep Space Nine (VHS)", Seasons.allOfSeries("DS9"))
			.put("Star Trek: Deep Space Nine (digital)", Seasons.allOfSeries("DS9"))
			.put("Star Trek: Deep Space Nine - The Collector's Edition", Seasons.allOfSeries("DS9"))
			.put("Star Trek: Deep Space Nine (LaserDisc)", Seasons.ofRange("DS9", 1, 3))
			.put("Star Trek: Enterprise (VHS)", Seasons.ofExactly("ENT", 1))
			.put("Star Trek 50th Anniversary TV and Movie Collection (Blu-ray)", Seasons.allOfSeries("TOS").andAllOfSeries("TAS"))
			.put("Star Trek 50th Anniversary TV and Movie Collection (DVD)", Seasons.allOfSeries("TOS").andAllOfSeries("TAS"))
			.put("Star Trek: The Next Generation (Betamax)", Seasons.ofExactly("TNG", 1, 2))
			.put("Star Trek: The Next Generation (VHS)", Seasons.allOfSeries("TNG"))
			.put("Star Trek: The Original Series (Betamax)", Seasons.allOfSeries("TOS"))
			.put("Star Trek: The Original Series (VHS)", Seasons.allOfSeries("TOS"))
			.put("Star Trek: The Original Series - Seasons 1-3 Remastered", Seasons.allOfSeries("TOS"))
			.put("Star Trek: Voyager (VHS)", Seasons.allOfSeries("VOY"))
			.put("TNG Season 4 US VHS", Seasons.ofExactly("TNG", 4))
			.put("The Ultimate Star Trek Collection", Seasons.allOfSeries("TOS").andAllOfSeries("TNG").andAllOfSeries("DS9").andAllOfSeries("VOY")
					.andAllOfSeries("ENT"))
			.build();

	private static final Map<String, Movies> MOVIE_SETS_TO_RANGES = ImmutableMap.<String, Movies>builder()
			.put("Star Trek: The Motion Picture/Star Trek II: The Wrath of Khan", Movies.ofRange(1, 2))
			.put("Star Trek 4-Movie Gift Set", Movies.ofRange(1, 4))
			.put("Star Trek: The Original 4-Movie Collection (4K Ultra HD)", Movies.ofRange(1, 4))
			.put("Star Trek: The Movies - 25th Anniversary Collectors Set", Movies.ofRange(1, 5))
			.put("Star Trek - The Original Crew Movie Collection (2001)", Movies.ofRange(1, 6))
			.put("Star Trek - The Original Crew Movie Collection (2002)", Movies.ofRange(1, 6))
			.put("Star Trek - The Original Crew Movie Collection (Special Edition)", Movies.ofRange(1, 6))
			.put("Star Trek - The Screen Voyages (LaserDisc)", Movies.ofRange(1, 6))
			.put("Star Trek - The Screen Voyages (VHS)", Movies.ofRange(1, 6))
			.put("Star Trek: Original Motion Picture Collection (DVD)", Movies.ofRange(1, 6))
			.put("Star Trek: The Movie Collection", Movies.ofRange(1, 6))
			.put("Star Trek: The Starfleet Collection", Movies.ofRange(1, 6))
			.put("Star Trek II: The Wrath of Khan/Star Trek IV: The Voyage Home (Blu-ray)", Movies.ofExactly(2, 4))
			.put("Star Trek II: The Wrath of Khan/Star Trek IV: The Voyage Home (DVD)", Movies.ofExactly(2, 4))
			.put("Star Trek: Motion Picture Trilogy (DVD)", Movies.ofRange(2, 4))
			.put("Star Trek III: The Search for Spock/Star Trek IV: The Voyage Home (DVD)", Movies.ofExactly(3, 4))
			.put("Star Trek - The Film Voyages IV-VI", Movies.ofRange(4, 6))
			.put("Star Trek V: The Final Frontier/Star Trek VI: The Undiscovered Country (DVD)", Movies.ofRange(5, 6))
			.put("Star Trek VI: The Undiscovered Country/Star Trek: First Contact (Blu-ray)", Movies.ofExactly(6, 8))
			.put("Star Trek VI: The Undiscovered Country/Star Trek: First Contact (DVD)", Movies.ofExactly(6, 8))
			.put("Star Trek: The Seven Screen Voyages", Movies.ofRange(1, 7))
			.put("Star Trek - The Movies", Movies.ofRange(1, 8))
			.put("Star Trek - Movie Collection", Movies.ofRange(1, 9))
			.put("Star Trek - Shuttle Box", Movies.ofRange(1, 9))
			.put("The Ultimate Star Trek Movie Collection (VHS)", Movies.ofRange(1, 9))
			.put("Star Trek - The Motion Pictures DVD Collection (2005)", Movies.ofRange(1, 10))
			.put("Star Trek: Legends of the Final Frontier Collection (Blu-ray)", Movies.ofRange(1, 10))
			.put("Star Trek: Legends of the Final Frontier Collection (DVD)", Movies.ofRange(1, 10))
			.put("Star Trek I-X - Limited Collector's Edition", Movies.ofRange(1, 10))
			.put("Star Trek: 10 Original Movies", Movies.ofRange(1, 10))
			.put("Star Trek: Stardate Collection (Blu-ray)", Movies.ofRange(1, 10))
			.put("Star Trek: Stardate Collection (DVD)", Movies.ofRange(1, 10))
			.put("The Ultimate Star Trek Collection", Movies.ofRange(1, 10))
			.put("The Ultimate Star Trek Movie Collection (Blu-ray)", Movies.ofRange(1, 11))
			.put("Star Trek Generations/Star Trek: First Contact (DVD)", Movies.ofRange(7, 8))
			.put("Star Trek Generations/Star Trek: First Contact (VHS)", Movies.ofRange(7, 8))
			.put("Star Trek: The Next Generation Collector's Set", Movies.ofRange(7, 9))
			.put("Star Trek: The Next Generation Motion Picture Collection (Blu-ray)", Movies.ofRange(7, 10)) // empty first
			.put("Star Trek: The Next Generation Motion Picture Collection (DVD)", Movies.ofRange(7, 10)) // empty first
			.put("Star Trek: Insurrection/Star Trek Nemesis (DVD)", Movies.ofRange(9, 10))
			.put("Star Trek: The Next Generation - The Complete TV Movies", Movies.none()) // no movies, just trailers
			.put("TNG Season 6 DVD", Movies.none()) // false positive
			.put("TNG Season 7 Blu-ray", Movies.none()) // false positive
			.put("TNG Season 7 DVD", Movies.none()) // false positive
			.put("The Best of Star Trek: The Next Generation (DVD)", Movies.none()) // false positive
			.put("The Best of Star Trek: The Next Generation, Volume 2", Movies.none()) // false positive
			.put("The Best of Star Trek: The Original Series", Movies.none()) // false positive
			.put("The Best of Star Trek: The Original Series, Volume 2", Movies.none()) // false positive
			.put("Ultimate Trek: Star Trek's Greatest Moments", Movies.none()) // false positive
			.put("William Shatner Presents: Chaos on the Bridge", Movies.none()) // false positive
			.put("William Shatner's Star Trek Memories", Movies.none()) // false positive
			.put("Your Guide To ... Star Trek Generations", Movies.none()) // documentary about
			.build();

	private final SeriesRepository seriesRepository;

	private final SeasonRepository seasonRepository;

	private final MovieRepository movieRepository;

	@Override
	public void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();
		final String pageTitle = page.getTitle();
		if (EMPTY_CONTENTS_TITLES.contains(pageTitle)) {
			videoTemplate.getSeries().removeIf(series -> true);
			videoTemplate.getSeasons().removeIf(season -> true);
			videoTemplate.getMovies().removeIf(movie -> true);
		}

		if (SERIES_SETS_TO_RANGES.containsKey(pageTitle)) {
			addSeries(videoTemplate.getSeries(), SERIES_SETS_TO_RANGES.get(pageTitle));
		}

		if (SEASONS_SETS_TO_RANGES.containsKey(pageTitle)) {
			addSeasons(videoTemplate.getSeasons(), SEASONS_SETS_TO_RANGES.get(pageTitle));
		}

		if (MOVIE_SETS_TO_RANGES.containsKey(pageTitle)) {
			addMovies(videoTemplate.getMovies(), MOVIE_SETS_TO_RANGES.get(pageTitle));
		}

		boolean documentary = Boolean.TRUE.equals(videoTemplate.getDocumentary());
		boolean specialFeatures = Boolean.TRUE.equals(videoTemplate.getSpecialFeatures());
		boolean hasSeries = !videoTemplate.getSeries().isEmpty();
		boolean hasSeasons = !videoTemplate.getSeasons().isEmpty();
		boolean hasMovies = !videoTemplate.getMovies().isEmpty();
		if ((documentary || specialFeatures) && (hasSeries || hasSeasons || hasMovies)) {
			log.warn("Contradicting findings for video release \"{}\": both documentary ({}) / special flag ({}) found, and non empty series ({})"
					+ " / seasons ({}) / movies ({}) list.", pageTitle, documentary, specialFeatures, hasSeries, hasSeasons, hasMovies);
		}
	}

	private void addSeries(Set<Series> series, SeriesList seriesList) {
		series.removeIf(movie -> true);
		for (String abbreviation : seriesList.getAbbreviations()) {
			seriesRepository.findByAbbreviation(abbreviation).ifPresent(series::add);
		}
	}

	private void addMovies(Set<Movie> movies, Movies movieRange) {
		movies.removeIf(movie -> true);
		for (Integer index : movieRange.getIndices()) {
			movieRepository.findByTitle(MovieTitle.movieAtIndex(index)).ifPresent(movies::add);
		}
	}

	private void addSeasons(Set<Season> seasons, Seasons seasonsRange) {
		seasons.removeIf(movie -> true);
		final List<Seasons.SeriesSeason> seriesSeasons = seasonsRange.getSeriesSeasons();
		for (Seasons.SeriesSeason seriesSeason : seriesSeasons) {
			String seriesAbbreviation = seriesSeason.getSeriesAbbreviation();
			Integer seasonNumber = seriesSeason.getSeasonNumber();
			if (seasonNumber == null) {
				seasons.addAll(seasonRepository.findBySeriesAbbreviation(seriesAbbreviation));
			} else {
				final Season season = seasonRepository.findBySeriesAbbreviationAndSeasonNumber(seriesAbbreviation, seasonNumber);
				if (season != null) {
					seasons.add(season);
				}
			}
		}
	}

	@AllArgsConstructor
	private static class Movies {

		@Getter
		private List<Integer> indices;

		private static Movies ofRange(int startIndex, int endIndex) {
			return new Movies(NumberUtil.inclusiveRangeOf(startIndex, endIndex));
		}

		private static Movies none() {
			return new Movies(List.of());
		}

		private static Movies ofExactly(int... indices) {
			List<Integer> indicesList = Lists.newArrayList();
			for (int index : indices) {
				indicesList.add(index);
			}
			return new Movies(indicesList);
		}

	}

	@AllArgsConstructor
	private static class SeriesList {

		@Getter
		private List<String> abbreviations;

		private static SeriesList ofAbbreviations(String... abbreviations) {
			return new SeriesList(Arrays.stream(abbreviations).toList());
		}

	}

	@AllArgsConstructor
	private static class Seasons {

		@Getter
		private final List<SeriesSeason> seriesSeasons;

		private static Seasons allOfSeries(String seriesAbbreviation) {
			return new Seasons(List.of(new SeriesSeason(seriesAbbreviation, null)));
		}

		private static Seasons ofRange(String seriesAbbreviation, int startIndex, int endIndex) {
			return new Seasons(NumberUtil.inclusiveRangeOf(startIndex, endIndex).stream()
					.map(season -> new SeriesSeason(seriesAbbreviation, season))
					.collect(Collectors.toList()));
		}

		private static Seasons ofExactly(String seriesAbbreviation, int... indices) {
			List<Integer> indicesList = Lists.newArrayList();
			for (int index : indices) {
				indicesList.add(index);
			}
			return new Seasons(indicesList.stream()
					.map(season -> new SeriesSeason(seriesAbbreviation, season))
					.collect(Collectors.toList()));
		}

		public Seasons andAllOfSeries(String seriesAbbreviation) {
			Seasons other = allOfSeries(seriesAbbreviation);
			List<SeriesSeason> combinedSeriesSeasons = Lists.newArrayList(this.seriesSeasons);
			combinedSeriesSeasons.addAll(other.seriesSeasons);
			return new Seasons(combinedSeriesSeasons);
		}

		@AllArgsConstructor
		private static class SeriesSeason {

			@Getter
			private final String seriesAbbreviation;

			@Getter
			private final Integer seasonNumber;

		}

	}

}
