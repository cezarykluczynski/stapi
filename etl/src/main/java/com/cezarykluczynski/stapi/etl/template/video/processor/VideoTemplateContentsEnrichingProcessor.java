package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.season.WikitextToSeasonProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.series.processor.WikitextToSeriesProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
@Slf4j
public class VideoTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoTemplate>> {

	private final VideoReleaseFormatProcessor videoReleaseFormatProcessor;

	private final WikitextToSeriesProcessor wikitextToSeriesProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final WikitextToSeasonProcessor wikitextToSeasonProcessor;

	@Inject
	public VideoTemplateContentsEnrichingProcessor(VideoReleaseFormatProcessor videoReleaseFormatProcessor,
			WikitextToSeriesProcessor wikitextToSeriesProcessor, NumberOfPartsProcessor numberOfPartsProcessor,
			WikitextToSeasonProcessor wikitextToSeasonProcessor) {
		this.videoReleaseFormatProcessor = videoReleaseFormatProcessor;
		this.wikitextToSeriesProcessor = wikitextToSeriesProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.wikitextToSeasonProcessor = wikitextToSeasonProcessor;
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
					videoTemplate.setSeries(wikitextToSeriesProcessor.process(value));
					break;
				case VideoTemplateParameter.SEASON:
					Set<Season> seasonSet = wikitextToSeasonProcessor.process(value);
					int seasonSetSize = seasonSet.size();
					if (seasonSetSize == 1) {
						videoTemplate.setSeason(seasonSet.iterator().next());
					} else if (seasonSetSize > 1) {
						log.warn("More than one season found for video {}, using none", title);
					} else {
						log.warn("No seasons found for video {}", title);
					}
					break;
				case VideoTemplateParameter.EPISODES:
					// TODO Should parse episodes and feature length episodes in a single processor
					break;
				case VideoTemplateParameter.DISCS:
					videoTemplate.setNumberOfDataCarriers(numberOfPartsProcessor.process(value));
					break;
				case VideoTemplateParameter.TIME:
				case VideoTemplateParameter.YEAR:
					// TODO
					break;
				default:
					break;
			}
		}
	}

}
