package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.EpisodesCountDTO;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.model.series.entity.Series;
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

	private final VideoReleaseNumberOfDataCarriersProcessor videoReleaseNumberOfDataCarriersProcessor;

	private final VideoTemplateEpisodesCountProcessor videoTemplateEpisodesCountProcessor;

	private final VideoTemplateYearsProcessor videoTemplateYearsProcessor;

	private final RunTimeProcessor runTimeProcessor;

	private final VideoTemplateSeasonProcessor videoTemplateSeasonProcessor;

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		// do series first to make sure seasons parsing have all the data required
		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			if (VideoTemplateParameter.SERIES.equals(key)) {
				List<Series> seriesList = wikitextToEntitiesProcessor.findSeries(part);
				videoTemplate.getSeries().addAll(seriesList);
			}
		}

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();
			switch (key) {
				case VideoTemplateParameter.FORMAT:
					videoTemplate.setFormat(videoReleaseFormatProcessor.process(value));
					break;
				case VideoTemplateParameter.SEASON:
					videoTemplate.getSeasons().addAll(videoTemplateSeasonProcessor.process(Pair.of(videoTemplate, value)));
					break;
				case VideoTemplateParameter.EPISODES:
					EpisodesCountDTO episodesCountDTO = videoTemplateEpisodesCountProcessor.process(value);
					videoTemplate.setNumberOfEpisodes(episodesCountDTO.getNumberOfEpisodes());
					videoTemplate.setNumberOfFeatureLengthEpisodes(episodesCountDTO.getNumberOfFeatureLengthEpisodes());
					break;
				case VideoTemplateParameter.DISCS:
					videoTemplate.setNumberOfDataCarriers(videoReleaseNumberOfDataCarriersProcessor.process(value));
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
