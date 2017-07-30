package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.service.VideoGameFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VideoGameTemplatePageProcessor implements ItemProcessor<Page, VideoGameTemplate> {

	private final VideoGameFilter videoGameFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final VideoGameTemplateCompositeEnrichingProcessor videoGameTemplateCompositeEnrichingProcessor;

	public VideoGameTemplatePageProcessor(VideoGameFilter videoGameFilter, TemplateFinder templateFinder, PageBindingService pageBindingService,
			VideoGameTemplateCompositeEnrichingProcessor videoGameTemplateCompositeEnrichingProcessor) {
		this.videoGameFilter = videoGameFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.videoGameTemplateCompositeEnrichingProcessor = videoGameTemplateCompositeEnrichingProcessor;
	}

	@Override
	public VideoGameTemplate process(Page item) throws Exception {
		if (videoGameFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_VIDEOGAME);

		if (!templateOptional.isPresent()) {
			log.warn("Could not find template {} on page {}", TemplateTitle.SIDEBAR_VIDEOGAME, item.getTitle());
			return null;
		}

		VideoGameTemplate videoGameTemplate = new VideoGameTemplate();
		videoGameTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		videoGameTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		videoGameTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(templateOptional.get(), videoGameTemplate));

		return videoGameTemplate;
	}

}
