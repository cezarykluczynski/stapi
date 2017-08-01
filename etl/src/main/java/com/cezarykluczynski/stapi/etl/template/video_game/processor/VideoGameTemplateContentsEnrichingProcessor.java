package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class VideoGameTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoGameTemplate>> {

	private final DatePartToLocalDateProcessor datePartToLocalDateProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	public VideoGameTemplateContentsEnrichingProcessor(DatePartToLocalDateProcessor datePartToLocalDateProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor) {
		this.datePartToLocalDateProcessor = datePartToLocalDateProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		VideoGameTemplate videoGameTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoGameTemplateParameter.TITLE:
					String originalTitle = videoGameTemplate.getTitle();
					if (StringUtils.isNotEmpty(value) && !StringUtils.equals(originalTitle, value)) {
						log.info("Changing video game title \"{}\" (taken from page title) to \"{}\" (taken from template)", originalTitle, value);
						videoGameTemplate.setTitle(value);
					}
					break;
				case VideoGameTemplateParameter.RELEASED:
					videoGameTemplate.setReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoGameTemplateParameter.YEAR:
					YearRange yearRange = wikitextToYearRangeProcessor.process(value);
					if (yearRange != null) {
						videoGameTemplate.setYearFrom(yearRange.getYearFrom());
						videoGameTemplate.setYearTo(yearRange.getYearTo());
					}
					break;
				case VideoGameTemplateParameter.STARDATE:
					StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
					if (stardateRange != null) {
						videoGameTemplate.setStardateFrom(stardateRange.getStardateFrom());
						videoGameTemplate.setStardateTo(stardateRange.getStardateTo());
					}
				case VideoGameTemplateParameter.REQUIREMENTS:
					videoGameTemplate.setSystemRequirements(value);
					break;
				default:
					break;
			}
		}
	}

}
