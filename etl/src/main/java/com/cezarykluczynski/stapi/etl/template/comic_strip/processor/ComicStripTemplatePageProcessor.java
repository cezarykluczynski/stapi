package com.cezarykluczynski.stapi.etl.template.comic_strip.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.character.WikitextSectionsCharactersProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComicStripTemplatePageProcessor implements ItemProcessor<Page, ComicStripTemplate> {

	private static final String COMIC_STRIP = "(comic strip";

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessor;

	private final WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor;

	public ComicStripTemplatePageProcessor(TemplateFinder templateFinder, PageBindingService pageBindingService,
			ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessor,
			WikitextSectionsCharactersProcessor wikitextSectionsCharactersProcessor) {
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.comicStripTemplatePartsEnrichingProcessor = comicStripTemplatePartsEnrichingProcessor;
		this.wikitextSectionsCharactersProcessor = wikitextSectionsCharactersProcessor;
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
		comicStripTemplate.getCharacters().addAll(wikitextSectionsCharactersProcessor.process(item));

		return comicStripTemplate;
	}

}
