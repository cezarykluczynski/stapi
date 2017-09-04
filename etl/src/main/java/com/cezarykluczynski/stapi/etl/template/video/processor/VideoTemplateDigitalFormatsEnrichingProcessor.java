package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoTemplateDigitalFormatsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<VideoTemplate> {

	private static final String YES = "yes";

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoTemplateParameter.DFAZ:
					videoTemplate.setAmazonDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFDM:
					videoTemplate.setDailymotionDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFGP:
					videoTemplate.setGooglePlayDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFIT:
					videoTemplate.setITunesDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFUV:
					videoTemplate.setUltraVioletDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFVO:
					videoTemplate.setVimeoDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFVU:
					videoTemplate.setVuduDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFXB:
					videoTemplate.setXboxSmartGlassDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFYT:
					videoTemplate.setYouTubeDigitalRelease(isYes(value));
					break;
				case VideoTemplateParameter.DFNF:
					videoTemplate.setNetflixDigitalRelease(isYes(value));
					break;
				default:
					break;
			}
		}
	}

	private static boolean isYes(String yesCandidate) {
		return StringUtils.equalsIgnoreCase(YES, yesCandidate);
	}

}
