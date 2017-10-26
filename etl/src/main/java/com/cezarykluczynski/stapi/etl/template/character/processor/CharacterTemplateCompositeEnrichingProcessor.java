package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxCharacterTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.fictional.processor.FictionalTemplateCompositeEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.hologram.processor.HologramTemplateCompositeEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryPlacesDTO;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplateCompositeEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePlacesFixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, CharacterTemplate>> {

	private final TemplateFinder templateFinder;

	private final IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor;

	private final CharacterTemplateFlagsEnrichingProcessor characterTemplateFlagsEnrichingProcessor;

	private final IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProvider;

	private final CharacterboxCharacterTemplateEnrichingProcessor characterboxCharacterTemplateEnrichingProcessor;

	private final HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessor;

	private final FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessor;

	public CharacterTemplateCompositeEnrichingProcessor(TemplateFinder templateFinder,
			IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor,
			CharacterTemplateFlagsEnrichingProcessor characterTemplateFlagsEnrichingProcessor,
			IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProvider,
			CharacterboxCharacterTemplateEnrichingProcessor characterboxCharacterTemplateEnrichingProcessor,
			HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessor,
			FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessor) {
		this.templateFinder = templateFinder;
		this.individualTemplateCompositeEnrichingProcessor = individualTemplateCompositeEnrichingProcessor;
		this.characterTemplateFlagsEnrichingProcessor = characterTemplateFlagsEnrichingProcessor;
		this.individualTemplatePlacesFixedValueProvider = individualTemplatePlacesFixedValueProvider;
		this.characterboxCharacterTemplateEnrichingProcessor = characterboxCharacterTemplateEnrichingProcessor;
		this.hologramTemplateCompositeEnrichingProcessor = hologramTemplateCompositeEnrichingProcessor;
		this.fictionalTemplateCompositeEnrichingProcessor = fictionalTemplateCompositeEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, CharacterTemplate> enrichablePair) throws Exception {
		CharacterTemplate characterTemplate = enrichablePair.getOutput();
		Page item = enrichablePair.getInput();

		characterTemplateFlagsEnrichingProcessor.enrich(EnrichablePair.of(item, characterTemplate));

		FixedValueHolder<IndividualLifeBoundaryPlacesDTO> individualLifeBoundaryPlacesDTOFixedValueHolder
				= individualTemplatePlacesFixedValueProvider.getSearchedValue(item.getTitle());

		if (individualLifeBoundaryPlacesDTOFixedValueHolder.isFound()) {
			IndividualLifeBoundaryPlacesDTO individualLifeBoundaryPlacesDTO = individualLifeBoundaryPlacesDTOFixedValueHolder.getValue();
			characterTemplate.setPlaceOfBirth(individualLifeBoundaryPlacesDTO.getPlaceOfBirth());
			characterTemplate.setPlaceOfDeath(individualLifeBoundaryPlacesDTO.getPlaceOfDeath());
		}

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_INDIVIDUAL);
		if (sidebarIndividualTemplateOptional.isPresent()) {
			Template sidebarIndividualTemplate = sidebarIndividualTemplateOptional.get();
			individualTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarIndividualTemplate, characterTemplate));
		}

		Optional<Template> memoryBetaTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.MBETA);
		if (memoryBetaTemplateOptional.isPresent()) {
			characterboxCharacterTemplateEnrichingProcessor.enrich(EnrichablePair.of(memoryBetaTemplateOptional.get(), characterTemplate));
		}

		Optional<Template> sidebarHologramTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_HOLOGRAM);
		if (sidebarHologramTemplateOptional.isPresent()) {
			hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplateOptional.get(), characterTemplate));
		}

		Optional<Template> sidebarFictionalTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_FICTIONAL);
		if (sidebarFictionalTemplateOptional.isPresent()) {
			fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarFictionalTemplateOptional.get(), characterTemplate));
		}
	}

}
