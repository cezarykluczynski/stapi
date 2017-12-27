package com.cezarykluczynski.stapi.etl.template.magazine.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazinePageFilter;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MagazineTemplatePageProcessor implements ItemProcessor<Page, MagazineTemplate> {

	private final TemplateFinder templateFinder;

	private final MagazinePageFilter magazinePageFilter;

	private final PageBindingService pageBindingService;

	private final MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessor;

	public MagazineTemplatePageProcessor(TemplateFinder templateFinder, MagazinePageFilter magazinePageFilter,
			PageBindingService pageBindingService, MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessor) {
		this.templateFinder = templateFinder;
		this.magazinePageFilter = magazinePageFilter;
		this.pageBindingService = pageBindingService;
		this.magazineTemplatePartsEnrichingProcessor = magazineTemplatePartsEnrichingProcessor;
	}

	@Override
	public MagazineTemplate process(Page item) throws Exception {
		if (magazinePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		String title = item.getTitle();
		MagazineTemplate magazineTemplate = new MagazineTemplate();
		magazineTemplate.setTitle(title);
		magazineTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> sidebarMagazineTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE,
				TemplateTitle.SIDEBAR_REFERENCE_BOOK);
		if (!sidebarMagazineTemplateOptional.isPresent()) {
			return magazineTemplate;
		}

		magazineTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarMagazineTemplateOptional.get().getParts(), magazineTemplate));

		return magazineTemplate;
	}

}
