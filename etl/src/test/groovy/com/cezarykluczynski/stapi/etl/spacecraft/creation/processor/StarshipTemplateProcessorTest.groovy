package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

class StarshipTemplateProcessorTest extends AbstractSpacecraftTest {

	private UidGenerator uidGeneratorMock

	private StarshipTemplateProcessor starshipTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		starshipTemplateProcessor = new StarshipTemplateProcessor(uidGeneratorMock)
	}

	void "converts StarshipTemplate to SpacecraftClass"() {
		given:
		Organization owner = Mock()
		Organization operator = Mock()
		SpacecraftClass spacecraftClass = Mock()
		StarshipTemplate starshipTemplate = new StarshipTemplate(
				name: NAME,
				registry: REGISTRY,
				status: STATUS,
				dateStatus: DATE_STATUS,
				page: page,
				owner: owner,
				operator: operator,
				spacecraftClass: spacecraftClass)

		when:
		Spacecraft spacecraft = starshipTemplateProcessor.process(starshipTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Spacecraft)
		0 * _
		spacecraft.name == NAME
		spacecraft.registry == REGISTRY
		spacecraft.status == STATUS
		spacecraft.dateStatus == DATE_STATUS
		spacecraft.page == page
		spacecraft.owner == owner
		spacecraft.operator == operator
		spacecraft.spacecraftClass == spacecraftClass
	}

}
