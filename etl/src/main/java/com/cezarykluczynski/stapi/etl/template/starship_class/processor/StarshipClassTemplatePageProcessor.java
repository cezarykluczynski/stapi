package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.service.SpacecraftClassPageFilter;
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
public class StarshipClassTemplatePageProcessor implements ItemProcessor<Page, StarshipClassTemplate> {

	private final SpacecraftClassPageFilter spacecraftClassPageFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final StarshipClassTemplateCategoriesEnrichingProcessor starshipClassTemplateCategoriesEnrichingProcessor;

	private final StarshipClassTemplateCompositeEnrichingProcessor starshipClassTemplateCompositeEnrichingProcessor;

	public StarshipClassTemplatePageProcessor(SpacecraftClassPageFilter spacecraftClassPageFilter, TemplateFinder templateFinder,
	                                          PageBindingService pageBindingService,
	                                          StarshipClassTemplateCategoriesEnrichingProcessor starshipClassTemplateCategoriesEnrichingProcessor,
	                                          StarshipClassTemplateCompositeEnrichingProcessor starshipClassTemplateCompositeEnrichingProcessor) {
		this.spacecraftClassPageFilter = spacecraftClassPageFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.starshipClassTemplateCategoriesEnrichingProcessor = starshipClassTemplateCategoriesEnrichingProcessor;
		this.starshipClassTemplateCompositeEnrichingProcessor = starshipClassTemplateCompositeEnrichingProcessor;
	}

	@Override
	public StarshipClassTemplate process(Page item) throws Exception {
		if (spacecraftClassPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate();
		starshipClassTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		starshipClassTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_STARSHIP_CLASS);

		starshipClassTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(item.getCategories(), starshipClassTemplate));

		if (templateOptional.isPresent()) {
			starshipClassTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(templateOptional.get(), starshipClassTemplate));
		}

		return starshipClassTemplate;
	}

}
