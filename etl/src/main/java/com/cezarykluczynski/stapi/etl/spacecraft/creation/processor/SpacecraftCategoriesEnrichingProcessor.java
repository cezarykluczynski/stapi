package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpacecraftCategoriesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<String>, StarshipTemplate>> {

	private static final Map<String, String> CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP = Maps.newHashMap();

	static {
		CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP.put(CategoryTitle.SPACE_STATIONS, PageTitle.SPACE_STATION);
		CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP.put(CategoryTitle.OUTPOSTS, PageTitle.OUTPOST);
		CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP.put(CategoryTitle.STARBASES, PageTitle.STARBASE);
	}

	private final SpacecraftTypeRepository spacecraftTypeRepository;

	public SpacecraftCategoriesEnrichingProcessor(SpacecraftTypeRepository spacecraftTypeRepository) {
		this.spacecraftTypeRepository = spacecraftTypeRepository;
	}

	public void enrich(EnrichablePair<List<String>, StarshipTemplate> enrichablePair) {
		List<String> categoryTitleList = enrichablePair.getInput();
		StarshipTemplate starshipTemplate = enrichablePair.getOutput();

		for (String categoryTitle : categoryTitleList) {
			if (CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP.containsKey(categoryTitle)) {
				String pageTitle = CATEGORY_TITLE_TO_SPACECRAFT_TYPE_TITLE_MAP.get(categoryTitle);
				Optional<SpacecraftType> spacecraftTypeOptional = spacecraftTypeRepository.findByNameIgnoreCase(pageTitle);
				spacecraftTypeOptional.ifPresent(spacecraftType -> starshipTemplate.getSpacecraftTypes().add(spacecraftType));
			}
		}
	}

}
