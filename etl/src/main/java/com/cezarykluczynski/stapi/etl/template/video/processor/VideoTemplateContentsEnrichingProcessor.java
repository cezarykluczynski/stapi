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
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VideoTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private final VideoReleaseFormatProcessor videoReleaseFormatProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final VideoTemplateEpisodesCountProcessor videoTemplateEpisodesCountProcessor;

	private final VideoTemplateYearsProcessor videoTemplateYearsProcessor;

	private final RunTimeProcessor runTimeProcessor;

	public VideoTemplateContentsEnrichingProcessor(VideoReleaseFormatProcessor videoReleaseFormatProcessor,
			WikitextToEntitiesProcessor wikitextToEntitiesProcessor, NumberOfPartsProcessor numberOfPartsProcessor,
			VideoTemplateEpisodesCountProcessor videoTeplateEpisodesCountProcessor, VideoTemplateYearsProcessor videoTemplateYearsProcessor,
			RunTimeProcessor runTimeProcessor) {
		this.videoReleaseFormatProcessor = videoReleaseFormatProcessor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.videoTemplateEpisodesCountProcessor = videoTeplateEpisodesCountProcessor;
		this.videoTemplateYearsProcessor = videoTemplateYearsProcessor;
		this.runTimeProcessor = runTimeProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();
		String title = videoTemplate.getTitle();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoTemplateParameter.FORMAT:
					videoTemplate.setFormat(videoReleaseFormatProcessor.process(value));
					break;
				case VideoTemplateParameter.SERIES:
					List<Series> seriesList = wikitextToEntitiesProcessor.findSeries(value);
					videoTemplate.setSeries(seriesList.isEmpty() ? null : seriesList.get(0));
					break;
				case VideoTemplateParameter.SEASON:
					List<Season> seasonList = wikitextToEntitiesProcessor.findSeasons(value);
					int seasonSetSize = seasonList.size();
					boolean hasNotBlankValue = StringUtils.isNotBlank(value);
					if (seasonSetSize == 1) {
						videoTemplate.setSeason(seasonList.iterator().next());
					} else if (hasNotBlankValue) {
						if (seasonSetSize > 1) {
							log.warn("More than one season found for video \"{}\", using none; value was: \"{}\"", title, value);
						} else {
							log.warn("No seasons found for video \"{}\", value was: \"{}\"", title, value);
						}
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
