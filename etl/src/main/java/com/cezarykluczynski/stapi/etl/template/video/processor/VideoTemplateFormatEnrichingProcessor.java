package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class VideoTemplateFormatEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private final VideoReleaseFormatFromCategoryLinkProcessor videoReleaseFormatFromCategoryLinkProcessor;

	@Inject
	public VideoTemplateFormatEnrichingProcessor(VideoReleaseFormatFromCategoryLinkProcessor videoReleaseFormatFromCategoryLinkProcessor) {
		this.videoReleaseFormatFromCategoryLinkProcessor = videoReleaseFormatFromCategoryLinkProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();
		VideoReleaseFormat videoReleaseFormatFromCategories = videoReleaseFormatFromCategoryLinkProcessor.process(page.getCategories());
		VideoReleaseFormat videoReleaseFormatFromSidebarVideoTemplate = videoTemplate.getFormat();

		if (videoReleaseFormatFromSidebarVideoTemplate == null) {
			videoTemplate.setFormat(videoReleaseFormatFromCategories);
		} else if (videoReleaseFormatFromCategories != null && videoReleaseFormatFromSidebarVideoTemplate != videoReleaseFormatFromCategories) {
			log.warn("VideoReleaseFormat {} concluced from page categories differs from format {} found in sidebar video template",
					videoReleaseFormatFromCategories, videoReleaseFormatFromSidebarVideoTemplate);
		}
	}

}
