package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.video_release.creation.service.VideoReleasePageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VideoTemplatePageProcessor implements ItemProcessor<Page, VideoTemplate> {

	private final VideoReleasePageFilter videoReleasePageFilter;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final VideoTemplateCompositeEnrichingProcessor videoTemplateCompositeEnrichingProcessor;

	private final VideoTemplateFormatEnrichingProcessor videoTemplateFormatEnrichingProcessor;

	public VideoTemplatePageProcessor(VideoReleasePageFilter videoReleasePageFilter, PageBindingService pageBindingService,
			TemplateFinder templateFinder, VideoTemplateCompositeEnrichingProcessor videoTemplateCompositeEnrichingProcessor,
			VideoTemplateFormatEnrichingProcessor videoTemplateFormatEnrichingProcessor) {
		this.videoReleasePageFilter = videoReleasePageFilter;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.videoTemplateCompositeEnrichingProcessor = videoTemplateCompositeEnrichingProcessor;
		this.videoTemplateFormatEnrichingProcessor = videoTemplateFormatEnrichingProcessor;
	}

	@Override
	public VideoTemplate process(Page item) throws Exception {
		if (videoReleasePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		VideoTemplate videoTemplate = new VideoTemplate();
		videoTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		videoTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));

		Optional<Template> sidebarVideoTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_VIDEO);

		if (sidebarVideoTemplateOptional.isPresent()) {
			videoTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplateOptional.get(), videoTemplate));
		} else {
			log.info("No sidebar video template found on page: \"{}\"", item.getTitle());
		}

		videoTemplateFormatEnrichingProcessor.enrich(EnrichablePair.of(item, videoTemplate));

		return videoTemplate;
	}

}
