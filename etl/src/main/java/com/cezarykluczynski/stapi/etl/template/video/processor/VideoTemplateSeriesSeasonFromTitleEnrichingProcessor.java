package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplateSeriesSeasonFromTitleEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private static final String SEASON = " Season ";

	private final SeriesRepository seriesRepository;
	private final SeasonRepository seasonRepository;

	private final Map<String, Long> titleToSeriesIdMappings = Maps.newLinkedHashMap();
	private final Map<String, Long> abbreviationToSeriesIdMappings = Maps.newLinkedHashMap();

	@Override
	@SuppressWarnings("NPathComplexity")
	public synchronized void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();
		loadSeriesCache();
		String title = page.getTitle();
		String normalizedTitle = normalizeTitle(title);
		if (titleToSeriesIdMappings.containsKey(normalizedTitle)) {
			// find a fresh entity to not mess up with Hibernate sessions
			seriesRepository.findById(titleToSeriesIdMappings.get(normalizedTitle)).ifPresent(videoTemplate::setSeries);
		}
		if (videoTemplate.getSeries() == null) {
			for (Map.Entry<String, Long> titleToSeriesEntry : titleToSeriesIdMappings.entrySet()) {
				if (normalizedTitle.startsWith(titleToSeriesEntry.getKey())) {
					seriesRepository.findById(titleToSeriesIdMappings.get(titleToSeriesEntry.getKey())).ifPresent(videoTemplate::setSeries);
				}
			}
		}
		if (videoTemplate.getSeries() == null) {
			String normalizedAbbreviation = normalizeAbbreviation(title);
			if (abbreviationToSeriesIdMappings.containsKey(normalizedAbbreviation)) {
				seriesRepository.findById(abbreviationToSeriesIdMappings.get(normalizedAbbreviation)).ifPresent(videoTemplate::setSeries);
			}
		}

		Series series = videoTemplate.getSeries();
		if (series != null && videoTemplate.getSeason() == null && StringUtils.containsIgnoreCase(title, SEASON)) {
			if (StringUtils.containsIgnoreCase(title, SEASON)) {
				String seasonNumber = StringUtils.substringBefore(StringUtils.substringAfter(title, SEASON), " ");
				Integer seasonNumberInteger = Ints.tryParse(seasonNumber);
				if (seasonNumberInteger != null && seasonNumberInteger < 10) {
					Season season = seasonRepository.findBySeriesAbbreviationAndSeasonNumber(series.getAbbreviation(), seasonNumberInteger);
					if (season != null) {
						videoTemplate.setSeason(season);
					}
				}
			}
		}
	}

	private String normalizeAbbreviation(String title) {
		if (StringUtils.containsNone(title, ' ')) {
			return null;
		}
		final String[] split = StringUtils.split(title, ' ');
		return split[0].toUpperCase();
	}

	private void loadSeriesCache() {
		if (titleToSeriesIdMappings.isEmpty()) {
			final List<Series> allSeries = seriesRepository.findAll();
			titleToSeriesIdMappings.putAll(allSeries.stream()
					.collect(Collectors.toMap(this::toNormalizedTitle, Series::getId)));
			abbreviationToSeriesIdMappings.putAll(allSeries.stream()
					.collect(Collectors.toMap(Series::getAbbreviation, Series::getId)));
		}
	}

	private String toNormalizedTitle(Series series) {
		return normalizeTitle(series.getTitle());
	}

	private String normalizeTitle(String title) {
		return StringUtils.trim(StringUtils.substringBefore(StringUtils.replaceAll(title, "(:|-\\s)", ""), "("));
	}

}
