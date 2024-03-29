package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.TemplateTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.element.creation.service.ElementPageFilter;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.element.processor.ElementTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElementPageProcessor implements ItemProcessor<Page, Element> {

	private final ElementPageFilter elementPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final TemplateFinder templateFinder;

	private final ElementTemplateEnrichingProcessor elementTemplateEnrichingProcessor;

	private final TemplateTitlesExtractingProcessor templateTitlesExtractingProcessor;

	private final ElementTemplateTitlesEnrichingProcessor elementTemplateTitlesEnrichingProcessor;

	private final ElementTransuranicProcessor elementTransuranicProcessor;

	private final ElementSymbolFixedValueProvider elementSymbolFixedValueProvider;

	@Override
	public Element process(Page item) throws Exception {
		if (elementPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Element element = new Element();
		element.setName(TitleUtil.getNameFromTitle(item.getTitle()));

		element.setPage(pageBindingService.fromPageToPageEntity(item));
		element.setUid(uidGenerator.generateFromPage(element.getPage(), Element.class));

		Optional<Template> sidebarElementTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_ELEMENT);
		if (sidebarElementTemplateOptional.isPresent()) {
			elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(sidebarElementTemplateOptional.get().getParts(), element));
		}

		elementTemplateTitlesEnrichingProcessor.enrich(EnrichablePair.of(templateTitlesExtractingProcessor.process(item.getTemplates()), element));

		element.setTransuranic(elementTransuranicProcessor.isTransuranic(item.getTitle()));

		FixedValueHolder<String> elementSymbolFixedValueHolder = elementSymbolFixedValueProvider.getSearchedValue(item.getTitle());
		if (elementSymbolFixedValueHolder.isFound()) {
			element.setSymbol(elementSymbolFixedValueHolder.getValue());
		}

		return element;
	}

}
