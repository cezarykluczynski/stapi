package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpeciesTemplateTypeWikitextEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<SpeciesTemplate> {

	private static final String UNKNOWN = "unknown";
	private static final String HUMANOID = "Humanoid";
	private static final String SHAPESHIFTER = "Shapeshifter";
	private static final String SAURIAN = "Saurian";
	private static final String NON_CORPOREAL_SPECIES = "Non-corporeal species";
	private static final String REPTILE = "Reptile";
	private static final String NON_HUMANOID = "Non-humanoid";

	private final WikitextApi wikitextApi;

	public SpeciesTemplateTypeWikitextEnrichingProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, SpeciesTemplate> enrichablePair) throws Exception {
		String wikitext = enrichablePair.getInput().getValue();
		SpeciesTemplate speciesTemplate = enrichablePair.getOutput();

		if (StringUtils.containsIgnoreCase(wikitext, UNKNOWN)) {
			return;
		}

		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);

		if (pageTitleList.contains(HUMANOID)) {
			speciesTemplate.setHumanoidSpecies(true);
		} else if (pageTitleList.contains(SAURIAN) || pageTitleList.contains(REPTILE)) {
			speciesTemplate.setReptilianSpecies(true);
		} else if (pageTitleList.contains(SHAPESHIFTER)) {
			speciesTemplate.setShapeshiftingSpecies(true);
		} else if (pageTitleList.contains(NON_CORPOREAL_SPECIES)) {
			speciesTemplate.setNonCorporealSpecies(true);
		} else if (!pageTitleList.contains(NON_HUMANOID)) {
			log.info("Could not get species type from wikitext: \"{}\"", wikitext);
		}
	}

}
