package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplateSeasonProcessor implements ItemProcessor<Pair<VideoTemplate, String>, Set<Season>> {

	private static final Pattern SEASONS_RANGE = Pattern.compile("^(\\d)\\s*(-|–|&ndash;)\\s*(\\d)$");

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final WikitextApi wikitextApi;

	private final SeasonRepository seasonRepository;

	@Override
	@NonNull
	public Set<Season> process(Pair<VideoTemplate, String> item) throws Exception {
		String value = item.getValue();
		if (StringUtils.isBlank(value)) {
			return Set.of();
		}

		VideoTemplate videoTemplate = item.getKey();
		if (videoTemplate.getSeries().size() == 1) {
			Series series = videoTemplate.getSeries().iterator().next();
			String abbreviation = series.getAbbreviation();
			Integer seasonNumber = Ints.tryParse(value);
			if (seasonNumber != null) {
				Season season = seasonRepository.findBySeriesAbbreviationAndSeasonNumber(abbreviation, seasonNumber);
				if (season != null) {
					return Set.of(season);
				}
			} else {
				final Matcher matcher = SEASONS_RANGE.matcher(wikitextApi.getWikitextWithoutLinks(value));
				if (matcher.matches()) {
					Integer from = Ints.tryParse(matcher.group(1));
					Integer to = Ints.tryParse(matcher.group(3));
					if (from != null && to != null) {
						final List<Integer> seasons = NumberUtil.inclusiveRangeOf(from, to);
						final List<Season> seasonsFromRange = seasons.stream()
								.map(seasonNumberFromRange -> seasonRepository
										.findBySeriesAbbreviationAndSeasonNumber(abbreviation, seasonNumberFromRange))
								.filter(Objects::nonNull)
								.collect(Collectors.toList());
						if (seasonsFromRange.size() != seasons.size()) {
							log.warn("For series {} there were {} seasons in the original range, but {} seasons were found ultimately: {}.",
									abbreviation, seasons.size(), seasonsFromRange.size(), seasonsFromRange.stream()
											.map(Season::getTitle)
											.collect(Collectors.toList()));
						}
						return Set.copyOf(seasonsFromRange);
					}
				}
			}
		}

		List<Season> seasonList = wikitextToEntitiesProcessor.findSeasons(value);
		if (!seasonList.isEmpty()) {
			return Set.copyOf(seasonList);
		}

		log.warn("No seasons found for video release \"{}\", value was: \"{}\"", videoTemplate.getTitle(), value);
		return Set.of();
	}

}
