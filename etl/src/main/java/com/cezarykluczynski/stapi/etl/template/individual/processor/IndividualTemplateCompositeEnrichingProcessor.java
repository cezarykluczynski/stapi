package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryPlacesDTO;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class IndividualTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, IndividualTemplate>> {

	private final TemplateFinder templateFinder;

	private final IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessor;

	private final IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor;

	private final IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor;

	private final IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProvider;

	@Inject
	public IndividualTemplateCompositeEnrichingProcessor(TemplateFinder templateFinder,
			IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessor,
			IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor,
			IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor,
			IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProvider) {
		this.templateFinder = templateFinder;
		this.individualTemplateDateOfDeathEnrichingProcessor = individualTemplateDateOfDeathEnrichingProcessor;
		this.individualTemplatePartsEnrichingProcessor = individualTemplatePartsEnrichingProcessor;
		this.individualMirrorAlternateUniverseEnrichingProcessor = individualMirrorAlternateUniverseEnrichingProcessor;
		this.individualTemplatePlacesFixedValueProvider = individualTemplatePlacesFixedValueProvider;
	}

	@Override
	public void enrich(EnrichablePair<Page, IndividualTemplate> enrichablePair) throws Exception {
		IndividualTemplate individualTemplate = enrichablePair.getOutput();
		Page item = enrichablePair.getInput();

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_INDIVIDUAL);

		FixedValueHolder<IndividualLifeBoundaryPlacesDTO> individualLifeBoundaryPlacesDTOFixedValueHolder
				= individualTemplatePlacesFixedValueProvider.getSearchedValue(item.getTitle());

		if (individualLifeBoundaryPlacesDTOFixedValueHolder.isFound()) {
			IndividualLifeBoundaryPlacesDTO individualLifeBoundaryPlacesDTO = individualLifeBoundaryPlacesDTOFixedValueHolder.getValue();
			individualTemplate.setPlaceOfBirth(individualLifeBoundaryPlacesDTO.getPlaceOfBirth());
			individualTemplate.setPlaceOfDeath(individualLifeBoundaryPlacesDTO.getPlaceOfDeath());
		}

		if (!sidebarIndividualTemplateOptional.isPresent()) {
			return;
		}

		Template template = sidebarIndividualTemplateOptional.get();

		individualTemplateDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate));
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(template.getParts(), individualTemplate));
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(item, individualTemplate));
	}

}
