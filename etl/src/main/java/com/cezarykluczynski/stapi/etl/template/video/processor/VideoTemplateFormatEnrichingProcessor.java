package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseFormatFixedValueProvider;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoTemplateFormatEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private final VideoReleaseFormatFixedValueProvider videoReleaseFormatFixedValueProvider;

	private final VideoReleaseFormatFromCategoryLinkProcessor videoReleaseFormatFromCategoryLinkProcessor;

	public VideoTemplateFormatEnrichingProcessor(VideoReleaseFormatFixedValueProvider videoReleaseFormatFixedValueProvider,
			VideoReleaseFormatFromCategoryLinkProcessor videoReleaseFormatFromCategoryLinkProcessor) {
		this.videoReleaseFormatFixedValueProvider = videoReleaseFormatFixedValueProvider;
		this.videoReleaseFormatFromCategoryLinkProcessor = videoReleaseFormatFromCategoryLinkProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		FixedValueHolder<VideoReleaseFormat> videoReleaseFormatFixedValueHolder = videoReleaseFormatFixedValueProvider
				.getSearchedValue(page.getTitle());
		if (videoReleaseFormatFixedValueHolder.isFound()) {
			videoTemplate.setFormat(videoReleaseFormatFixedValueHolder.getValue());
			return;
		}

		VideoReleaseFormat videoReleaseFormatFromCategories = videoReleaseFormatFromCategoryLinkProcessor.process(page.getCategories());
		VideoReleaseFormat videoReleaseFormatFromSidebarVideoTemplate = videoTemplate.getFormat();

		if (videoReleaseFormatFromSidebarVideoTemplate == null) {
			videoTemplate.setFormat(videoReleaseFormatFromCategories);
		} else if (videoReleaseFormatFromCategories != null && videoReleaseFormatFromSidebarVideoTemplate != videoReleaseFormatFromCategories) {
			log.warn("VideoReleaseFormat {} concluded from page categories differs from format {} found in sidebar video template for page \"{}\"",
					videoReleaseFormatFromCategories, videoReleaseFormatFromSidebarVideoTemplate, page.getTitle());
		}
	}

}
