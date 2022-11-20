package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplateSeasonProcessor implements ItemProcessor<Pair<VideoTemplate, String>, Season> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final SeasonRepository seasonRepository;

	@Override
	public Season process(Pair<VideoTemplate, String> item) throws Exception {
		String value = item.getValue();
		if (StringUtils.isBlank(value)) {
			return null;
		}

		VideoTemplate videoTemplate = item.getKey();
		if (videoTemplate.getSeries() != null && StringUtils.isNumeric(value)) {
			Integer seasonNumber = Ints.tryParse(value);
			if (seasonNumber != null) {
				Series series = videoTemplate.getSeries();
				Season season = seasonRepository.findBySeriesAbbreviationAndSeasonNumber(series.getAbbreviation(), seasonNumber);
				if (season != null) {
					return season;
				}
			}
		}

		List<Season> seasonList = wikitextToEntitiesProcessor.findSeasons(value);
		int seasonSetSize = seasonList.size();
		String title = videoTemplate.getTitle();
		if (seasonSetSize == 1) {
			return seasonList.iterator().next();
		} else if (seasonSetSize > 1) {
			log.warn("More than one season found for video \"{}\", using none; value was: \"{}\"", title, value);
		} else {
			log.warn("No seasons found for video \"{}\", value was: \"{}\"", title, value);
		}
		return null;
	}

}
