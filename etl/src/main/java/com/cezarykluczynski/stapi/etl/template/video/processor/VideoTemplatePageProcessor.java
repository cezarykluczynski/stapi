package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.video_release.creation.service.VideoReleasePageFilter;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoTemplatePageProcessor implements ItemProcessor<Page, VideoTemplate> {

	private final VideoReleasePageFilter videoReleasePageFilter;

	private final VideoTemplateSeriesSeasonFromTitleEnrichingProcessor videoTemplateSeriesSeasonFromTitleEnrichingProcessor;

	private final VideoTemplateContentsFromWikitextEnrichingProcessor videoTemplateContentsFromWikitextEnrichingProcessor;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final VideoTemplateTitleEnrichingProcessor videoTemplateTitleEnrichingProcessor;

	private final VideoTemplateCompositeEnrichingProcessor videoTemplateCompositeEnrichingProcessor;

	private final VideoTemplateFormatEnrichingProcessor videoTemplateFormatEnrichingProcessor;

	private final VideoTemplateCategoriesEnrichingProcessor videoTemplateCategoriesEnrichingProcessor;

	private final VideoTemplateContentsFixedValueEnricher videoTemplateContentsFixedValueEnricher;

	@Override
	public VideoTemplate process(Page item) throws Exception {
		if (videoReleasePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		VideoTemplate videoTemplate = new VideoTemplate();
		videoTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		videoTemplateTitleEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));
		videoTemplateContentsFromWikitextEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));

		Optional<Template> sidebarVideoTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_VIDEO);
		if (sidebarVideoTemplateOptional.isPresent()) {
			videoTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplateOptional.get(), videoTemplate));
		}

		videoTemplateFormatEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));
		videoTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));
		videoTemplateContentsFixedValueEnricher.enrich(EnrichablePair.of(item, videoTemplate));

		return videoTemplate;
	}

}
