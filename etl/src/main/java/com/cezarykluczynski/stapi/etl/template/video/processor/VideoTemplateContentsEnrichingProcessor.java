package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.EpisodesCountDTO;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private final VideoReleaseFormatProcessor videoReleaseFormatProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final VideoTemplateEpisodesCountProcessor videoTemplateEpisodesCountProcessor;

	private final VideoTemplateYearsProcessor videoTemplateYearsProcessor;

	private final RunTimeProcessor runTimeProcessor;

	private final VideoTemplateSeasonProcessor videoTemplateSeasonProcessor;

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoTemplateParameter.FORMAT:
					videoTemplate.setFormat(videoReleaseFormatProcessor.process(value));
					break;
				case VideoTemplateParameter.SERIES:
					if (videoTemplate.getSeries() == null) {
						List<Series> seriesList = wikitextToEntitiesProcessor.findSeries(value);
						if (seriesList.size() == 1) {
							videoTemplate.setSeries(seriesList.get(0));
						}
					}
					break;
				case VideoTemplateParameter.SEASON:
					if (videoTemplate.getSeason() == null) {
						videoTemplate.setSeason(videoTemplateSeasonProcessor.process(Pair.of(videoTemplate, value)));
					}
					break;
				case VideoTemplateParameter.EPISODES:
					EpisodesCountDTO episodesCountDTO = videoTemplateEpisodesCountProcessor.process(value);
					videoTemplate.setNumberOfEpisodes(episodesCountDTO.getNumberOfEpisodes());
					videoTemplate.setNumberOfFeatureLengthEpisodes(episodesCountDTO.getNumberOfFeatureLengthEpisodes());
					break;
				case VideoTemplateParameter.DISCS:
					videoTemplate.setNumberOfDataCarriers(numberOfPartsProcessor.process(value));
					break;
				case VideoTemplateParameter.TIME:
					videoTemplate.setRunTime(runTimeProcessor.process(value));
					break;
				case VideoTemplateParameter.YEAR:
					YearRange yearRange = videoTemplateYearsProcessor.process(part);
					if (yearRange != null) {
						videoTemplate.setYearFrom(yearRange.getYearFrom());
						videoTemplate.setYearTo(yearRange.getYearTo());
					}
					break;
				default:
					break;
			}
		}
	}

}
