package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class VideoTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoTemplate>> {

	private final VideoReleaseFormatProcessor videoReleaseFormatProcessor;

	@Inject
	public VideoTemplateContentsEnrichingProcessor(VideoReleaseFormatProcessor videoReleaseFormatProcessor) {
		this.videoReleaseFormatProcessor = videoReleaseFormatProcessor;
	}

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
				case VideoTemplateParameter.SEASON:
				case VideoTemplateParameter.EPISODES:
				case VideoTemplateParameter.DISCS:
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
