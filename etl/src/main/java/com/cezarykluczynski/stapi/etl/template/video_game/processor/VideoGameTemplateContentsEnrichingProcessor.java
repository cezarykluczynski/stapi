package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class VideoGameTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoGameTemplate> {

	private final DatePartToLocalDateProcessor datePartToLocalDateProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private final WikitextApi wikitextApi;

	VideoGameTemplateContentsEnrichingProcessor(DatePartToLocalDateProcessor datePartToLocalDateProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			WikitextApi wikitextApi) {
		this.datePartToLocalDateProcessor = datePartToLocalDateProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.wikitextApi = wikitextApi;
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
					if (StringUtils.isNotBlank(value)) {
						final String titleFromWikitext = wikitextApi.getWikitextWithoutLinks(value);
						String originalTitle = videoGameTemplate.getTitle();
						if (!StringUtils.equals(originalTitle, titleFromWikitext)) {
							log.info("Changing video game title \"{}\" (taken from page title) to \"{}\" (taken from template)",
									originalTitle, titleFromWikitext);
							videoGameTemplate.setTitle(titleFromWikitext);
						}
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
					break;
				case VideoGameTemplateParameter.REQUIREMENTS:
					if (StringUtils.isNotBlank(value)) {
						videoGameTemplate.setSystemRequirements(value);
					}
					break;
				default:
					break;
			}
		}
	}

}
