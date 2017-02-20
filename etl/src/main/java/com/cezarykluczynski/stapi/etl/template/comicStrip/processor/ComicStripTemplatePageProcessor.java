package com.cezarykluczynski.stapi.etl.template.comicStrip.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class ComicStripTemplatePageProcessor implements ItemProcessor<Page, ComicStripTemplate> {

	private static final String COMIC_STRIP = "(comic strip";

	private TemplateFinder templateFinder;

	private PageBindingService pageBindingService;

	private ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessor;

	@Inject
	public ComicStripTemplatePageProcessor(TemplateFinder templateFinder, PageBindingService pageBindingService,
			ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessor) {
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.comicStripTemplatePartsEnrichingProcessor = comicStripTemplatePartsEnrichingProcessor;
	}

	@Override
	public ComicStripTemplate process(Page item) throws Exception {
		Optional<Template> sidebarComicStripTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_COMIC_STRIP);

		if (!sidebarComicStripTemplateOptional.isPresent()) {
			return null;
		}

		Template template = sidebarComicStripTemplateOptional.get();

		String title = item.getTitle();
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate();
		comicStripTemplate.setTitle(StringUtils.contains(title, COMIC_STRIP) ? TitleUtil.getNameFromTitle(title) : title);
		comicStripTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(template.getParts(), comicStripTemplate));

		return comicStripTemplate;
	}

}
