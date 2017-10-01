package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest
import com.google.common.collect.Sets

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
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()
		SpacecraftType spacecraftType3 = Mock()
		SpacecraftType spacecraftType4 = Mock()
		SpacecraftClass spacecraftClass = new SpacecraftClass(spacecraftTypes: Sets.newHashSet(spacecraftType1, spacecraftType2))
		StarshipTemplate starshipTemplate = new StarshipTemplate(
				name: NAME,
				registry: REGISTRY,
				status: STATUS,
				dateStatus: DATE_STATUS,
				page: page,
				owner: owner,
				operator: operator,
				spacecraftClass: spacecraftClass,
				spacecraftTypes: Sets.newHashSet(spacecraftType3, spacecraftType4))

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
		spacecraft.spacecraftTypes.size() == 4
		spacecraft.spacecraftTypes.contains spacecraftType1
		spacecraft.spacecraftTypes.contains spacecraftType2
		spacecraft.spacecraftTypes.contains spacecraftType3
		spacecraft.spacecraftTypes.contains spacecraftType4
	}

	void "null spacecraft class is tolerated"() {
		given:
		StarshipTemplate starshipTemplate = new StarshipTemplate(
				name: NAME,
				page: page,
				spacecraftClass: null)

		when:
		Spacecraft spacecraft = starshipTemplateProcessor.process(starshipTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Spacecraft)
		0 * _
		spacecraft.name == NAME
		spacecraft.spacecraftClass == null
	}

}
