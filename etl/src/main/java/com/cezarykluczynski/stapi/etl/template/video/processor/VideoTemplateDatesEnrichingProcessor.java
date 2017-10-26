package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateDatesEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private final DatePartToLocalDateProcessor datePartToLocalDateProcessor;

	public VideoTemplateDatesEnrichingProcessor(DatePartToLocalDateProcessor datePartToLocalDateProcessor) {
		this.datePartToLocalDateProcessor = datePartToLocalDateProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();

			switch (key) {
				case VideoTemplateParameter.DATE0:
					videoTemplate.setRegionFreeReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE:
					videoTemplate.setRegion1AReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE1S:
					videoTemplate.setRegion1SlimlineReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE2:
					videoTemplate.setRegion2BReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE2S:
					videoTemplate.setRegion2SlimlineReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE4:
					videoTemplate.setRegion4AReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case VideoTemplateParameter.DATE4S:
					videoTemplate.setRegion4SlimlineReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				default:
					break;
			}
		}
	}

}
