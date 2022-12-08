package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
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
	private static final String SHAPESHIFTING_SPECIES = "Shapeshifting species";
	private static final String SHAPE_CHANGER = "Shape-changer";
	private static final String REPTILE = "Reptile";
	private static final String REPTILIAN = "Reptilian";
	private static final String REPTILOID = "Reptiloid";
	private static final String SAURIAN = "Saurian";
	private static final String NON_CORPOREAL_SPECIES = "Non-corporeal species";
	private static final String NON_CORPOREAL = "Non-corporeal";
	private static final String NON_CORPOREAL_LIFEFORM = "Non-corporeal lifeform";
	private static final String NON_HUMANOID = "Non-humanoid";
	private static final String ORNITHOID = "Ornithoid";
	private static final String AVIAN = "Avian";
	private static final String BIRD = "Bird";

	private final WikitextApi wikitextApi;

	public SpeciesTemplateTypeWikitextEnrichingProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	@SuppressWarnings({"CyclomaticComplexity", "NPathComplexity"})
	public void enrich(EnrichablePair<Template.Part, SpeciesTemplate> enrichablePair) throws Exception {
		String wikitext = enrichablePair.getInput().getValue();
		SpeciesTemplate speciesTemplate = enrichablePair.getOutput();

		if (StringUtils.containsIgnoreCase(wikitext, UNKNOWN)) {
			return;
		}

		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);

		boolean someFlagSet = false;
		if (StringUtil.containsIgnoreCase(pageTitleList, HUMANOID) || StringUtils.equalsIgnoreCase(HUMANOID, wikitext)) {
			speciesTemplate.setHumanoidSpecies(true);
			someFlagSet = true;
		}
		if (StringUtil.containsIgnoreCase(pageTitleList, SAURIAN) || StringUtil.containsIgnoreCase(pageTitleList, REPTILE)
				|| StringUtil.containsIgnoreCase(pageTitleList, REPTILIAN) || StringUtil.containsIgnoreCase(pageTitleList, REPTILOID)) {
			speciesTemplate.setReptilianSpecies(true);
			someFlagSet = true;
		}
		if (StringUtil.containsIgnoreCase(pageTitleList, SHAPESHIFTER) || StringUtil.containsIgnoreCase(pageTitleList, SHAPESHIFTING_SPECIES)
				|| StringUtil.containsIgnoreCase(pageTitleList, SHAPE_CHANGER)) {
			speciesTemplate.setShapeshiftingSpecies(true);
			someFlagSet = true;
		}
		if (StringUtil.containsIgnoreCase(pageTitleList, NON_CORPOREAL_SPECIES) || StringUtil.containsIgnoreCase(pageTitleList, NON_CORPOREAL)
				|| StringUtil.containsIgnoreCase(pageTitleList, NON_CORPOREAL_LIFEFORM)) {
			speciesTemplate.setNonCorporealSpecies(true);
			someFlagSet = true;
		}
		if (StringUtil.containsIgnoreCase(pageTitleList, ORNITHOID) || StringUtil.containsIgnoreCase(pageTitleList, AVIAN)
				|| StringUtil.containsIgnoreCase(pageTitleList, BIRD)) {
			speciesTemplate.setAvianSpecies(true);
			someFlagSet = true;
		}
		if (!StringUtil.containsIgnoreCase(pageTitleList, NON_HUMANOID) && !someFlagSet) {
			String pageTitle = "[unknown]";
			if (speciesTemplate.getPage() != null && speciesTemplate.getPage().getTitle() != null) {
				pageTitle = speciesTemplate.getPage().getTitle();
			}
			log.info("Could not get species type from wikitext: \"{}\" for page {}.", wikitext, pageTitle);
		}
	}

}
