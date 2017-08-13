package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

class StarshipClassTemplateProcessorTest extends AbstractSpacecraftTest {

	private UidGenerator uidGeneratorMock

	private StarshipClassTemplateProcessor starshipClassTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		starshipClassTemplateProcessor = new StarshipClassTemplateProcessor(uidGeneratorMock)
	}

	void "converts StarshipClassTemplate to SpacecraftClass"() {
		given:
		Species species = Mock()
		Organization organization = Mock()
		SpacecraftType spacecraftType = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(
				name: NAME,
				page: page,
				affiliatedSpecies: species,
				affiliatedOrganization: organization,
				spacecraftType: spacecraftType,
				numberOfDecks: NUMBER_OF_DECKS,
				warpCapable: WARP_CAPABLE,
				activeFrom: ACTIVE_FROM,
				activeTo: ACTIVE_TO)

		when:
		SpacecraftClass spacecraftClass = starshipClassTemplateProcessor.process(starshipClassTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, SpacecraftClass)
		0 * _
		spacecraftClass.name == NAME
		spacecraftClass.page == page
		spacecraftClass.affiliatedSpecies == species
		spacecraftClass.affiliatedOrganization == organization
		spacecraftClass.spacecraftType == spacecraftType
		spacecraftClass.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClass.warpCapable == WARP_CAPABLE
		spacecraftClass.activeFrom == ACTIVE_FROM
		spacecraftClass.activeTo == ACTIVE_TO
	}

}
