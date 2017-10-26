package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpeciesTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<SpeciesTemplate> {

	private static final String EXTINCT = "extinct";

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	private final SpeciesTemplateTypeWikitextEnrichingProcessor speciesTemplateTypeWikitextEnrichingProcessor;

	public SpeciesTemplatePartsEnrichingProcessor(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService,
			SpeciesTemplateTypeWikitextEnrichingProcessor speciesTemplateTypeWikitextEnrichingProcessor) {
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
		this.speciesTemplateTypeWikitextEnrichingProcessor = speciesTemplateTypeWikitextEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, SpeciesTemplate> enrichablePair) throws Exception {
		SpeciesTemplate speciesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case SpeciesTemplateParameter.NAME:
					speciesTemplate.setName(value);
					break;
				case SpeciesTemplateParameter.PLANET:
				case SpeciesTemplateParameter.QUADRANT:
					trySetAstronomicalObjectFromWikitext(speciesTemplate, part);
					break;
				case SpeciesTemplateParameter.TYPE:
					speciesTemplateTypeWikitextEnrichingProcessor.enrich(EnrichablePair.of(part, speciesTemplate));
					break;
				case SpeciesTemplateParameter.POPULATION:
					if (value.equalsIgnoreCase(EXTINCT)) {
						speciesTemplate.setExtinctSpecies(true);
					} else {
						log.info("Skipped population value \"{}\" for species \"{}\"", value, speciesTemplate.getName());
					}
					break;
				default:
					break;
			}
		}
	}

	private void trySetAstronomicalObjectFromWikitext(SpeciesTemplate speciesTemplate, Template.Part part) {
		List<String> linkTitleList = wikitextApi.getPageTitlesFromWikitext(part.getValue());

		if (!linkTitleList.isEmpty()) {
			entityLookupByNameService
					.findAstronomicalObjectByName(linkTitleList.get(0), MediaWikiSource.MEMORY_ALPHA_EN)
					.ifPresent(astronomicalObject -> {
						if (part.getKey().equals(SpeciesTemplateParameter.PLANET)) {
							speciesTemplate.setHomeworld(astronomicalObject);
						} else {
							speciesTemplate.setQuadrant(astronomicalObject);
						}
					});
		}
	}

}
