package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftCategoriesEnrichingProcessorTest extends Specification {

	private SpacecraftTypeRepository spacecraftTypeRepositoryMock

	private SpacecraftCategoriesEnrichingProcessor spacecraftCategoriesEnrichingProcessor

	void setup() {
		spacecraftTypeRepositoryMock = Mock()
		spacecraftCategoriesEnrichingProcessor = new SpacecraftCategoriesEnrichingProcessor(spacecraftTypeRepositoryMock)
	}

	void "adds spacecraft types from category to spacecraft mapping, if there were found"() {
		given:
		List<String> categoryTitleList = Lists.newArrayList(CategoryTitle.STARBASES, CategoryTitle.OUTPOSTS, CategoryTitle.ESCAPE_PODS,
				CategoryTitle.SPACE_STATIONS)
		StarshipTemplate starshipTemplate = new StarshipTemplate()
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()

		when:
		spacecraftCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitleList, starshipTemplate))

		then:
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PageTitle.STARBASE) >> Optional.of(spacecraftType1)
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PageTitle.OUTPOST) >> Optional.empty()
		1 * spacecraftTypeRepositoryMock.findByNameIgnoreCase(PageTitle.SPACE_STATION) >> Optional.of(spacecraftType2)
		0 * _
		starshipTemplate.spacecraftTypes.size() == 2
		starshipTemplate.spacecraftTypes.contains spacecraftType1
		starshipTemplate.spacecraftTypes.contains spacecraftType2
	}

}
