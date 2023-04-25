package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.util.constant.MovieTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.primitives.Longs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplateContentsFromWikitextEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private static final Long TOO_GENERIC_TITLE = 11L;
	private static final String CONTENTS = "Contents";
	private static final Set<String> MOVIE_CONTENTS_SECTIONS = Set.of(CONTENTS);
	private static final Set<String> SEASONS_CONTENTS_SECTIONS = Set.of(CONTENTS, "Season_releases", "Releases");
	private static final String SPACE_AND_BRACKET = " (";

	private static final List<String> SEARCH_ALL_CONTENTS_PAGE_TITLES = List.of(
			"Star Trek - Celebrating 40 Years - Special Collection",
			"Star Trek - Next Generation Movie Box, Special Collectors Edition (DVD)",
			"Star Trek - Next Generation Movie Collection",
			"Star Trek - The Motion Pictures DVD Collection (2001)",
			"Star Trek - The Motion Pictures DVD Collection (2002)",
			"Star Trek - The Motion Pictures DVD Collection (2003)",
			"Star Trek - The Movies I-X - Special Collector's Edition"
	);

	private final MovieRepository movieRepository;

	private final SeasonRepository seasonRepository;

	private final TemplateFinder templateFinder;

	private final WikitextApi wikitextApi;

	private List<Movie> movies;

	private List<Season> seasons;

	@Override
	@SuppressWarnings({"NPathComplexity", "CyclomaticComplexity"})
	public synchronized void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		if (movies == null) {
			movies = movieRepository.findAll();
		}
		if (seasons == null) {
			seasons = seasonRepository.findAll();
		}

		final Page page = enrichablePair.getInput();
		final String pageTitle = page.getTitle();
		final VideoTemplate videoTemplate = enrichablePair.getOutput();
		for (Movie movie : movies) {
			String movieTitle = movie.getTitle();
			String starTrekTitle = MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(TOO_GENERIC_TITLE);
			boolean startsWithAndIsNotStarTrekMovie = pageTitle.startsWith(movieTitle) && !Objects.equals(movie.getTitle(), starTrekTitle);
			boolean equalsBeforeBrackets = pageTitle.contains(SPACE_AND_BRACKET)
					&& org.apache.commons.lang3.StringUtils.substringBefore(pageTitle, SPACE_AND_BRACKET).equals(movieTitle);
			if (startsWithAndIsNotStarTrekMovie || equalsBeforeBrackets) {
				final Optional<Movie> movieOptional = movieRepository.findById(movie.getId());
				if (movieOptional.isPresent()) {
					Movie foundMovie = movieOptional.get();
					videoTemplate.getMovies().add(foundMovie);
					return;
				}
			}
		}

		final List<PageSection> movieContentsSections = page.getSections().stream()
				.filter(pageSection -> MOVIE_CONTENTS_SECTIONS.contains(pageSection.getAnchor()))
				.collect(Collectors.toList());
		final List<PageSection> seasonsContentsSections = page.getSections().stream()
				.filter(pageSection -> SEASONS_CONTENTS_SECTIONS.contains(pageSection.getAnchor()))
				.collect(Collectors.toList());

		if (SEARCH_ALL_CONTENTS_PAGE_TITLES.contains(pageTitle)) {
			PageSection pageSection = new PageSection();
			pageSection.setWikitext(page.getWikitext());
			movieContentsSections.add(pageSection);
			seasonsContentsSections.add(pageSection);
		}

		List<List<PageLink>> moviePageLinksToCheck = getPageLinksToCheck(movieContentsSections);
		List<List<PageLink>> seasonsPageLinksToCheck = getPageLinksToCheck(seasonsContentsSections);

		for (List<PageLink> pageLinks : moviePageLinksToCheck) {
			for (PageLink pageLink : pageLinks) {
				String pageLinkTitleToCompare = StringUtil.substringBeforeAll(pageLink.getTitle(), List.of(SPACE_AND_BRACKET));
				for (Movie movie : movies) {
					if (pageLink.getTitle() != null && pageLinkTitleToCompare.equalsIgnoreCase(movie.getTitle())) {
						movieRepository.findById(movie.getId()).ifPresent(foundMovie -> {
							videoTemplate.getMovies().add(foundMovie);
						});
					}
				}
			}
		}

		for (List<PageLink> pageLinks : seasonsPageLinksToCheck) {
			for (PageLink pageLink : pageLinks) {
				String pageLinkTitleToCompare = StringUtil.substringBeforeAll(pageLink.getTitle(), List.of(SPACE_AND_BRACKET));
				for (Season season : seasons) {
					if (pageLink.getTitle() != null && StringUtils.startsWithIgnoreCase(pageLinkTitleToCompare, season.getTitle())) {
						seasonRepository.findById(season.getId()).ifPresent(foundSeason -> {
							videoTemplate.getSeasons().add(foundSeason);
						});
					}
				}
			}
		}

		final List<Template> templates = templateFinder.findTemplates(page, TemplateTitle.FILM);
		for (Template template : templates) {
			final List<Template.Part> parts = template.getParts();
			String possibleIdRaw = parts.get(0).getValue();
			Long possibleId = Longs.tryParse(possibleIdRaw);
			if (possibleId != null) {
				final String titleCandidate = MovieTitle.FILM_TEMPLATE_TO_MOVIE_TITLE_MAP.get(possibleId);
				if (titleCandidate != null) {
					for (Movie movie : movies) {
						if (titleCandidate.equals(movie.getTitle())) {
							final Optional<Movie> movieOptional = movieRepository.findById(movie.getId());
							if (movieOptional.isPresent()) {
								final Movie foundMovie = movieOptional.get();
								videoTemplate.getMovies().add(foundMovie);
							}
						}
					}
				} else {
					log.info("Possible film ID {} was found, but no associated movie title was found", possibleId);
				}
			} else {
				log.info("Could not parse possible raw ID {} to an film ID.", possibleIdRaw);
			}
		}
	}

	private List<List<PageLink>> getPageLinksToCheck(List<PageSection> movieContentsSections) {
		return movieContentsSections.stream()
				.map(PageSection::getWikitext)
				.map(wikitext -> StringUtil.substringBeforeAll(wikitext, List.of("{{Video nav", "{{video nav")))
				.map(wikitextApi::getPageLinksFromWikitext)
				.collect(Collectors.toList());
	}

}
