package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest
import com.google.common.collect.Sets

class StarshipClassTemplateProcessorTest extends AbstractSpacecraftClassTest {

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
		Organization owner = Mock()
		Organization operator = Mock()
		Organization affiliation = Mock()
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(
				name: NAME,
				page: page,
				species: species,
				owner: owner,
				operator: operator,
				affiliation: affiliation,
				spacecraftTypes: Sets.newHashSet(spacecraftType1, spacecraftType2),
				numberOfDecks: NUMBER_OF_DECKS,
				warpCapable: WARP_CAPABLE,
				alternateReality: ALTERNATE_REALITY,
				activeFrom: ACTIVE_FROM,
				activeTo: ACTIVE_TO)

		when:
		SpacecraftClass spacecraftClass = starshipClassTemplateProcessor.process(starshipClassTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, SpacecraftClass)
		0 * _
		spacecraftClass.name == NAME
		spacecraftClass.page == page
		spacecraftClass.species == species
		spacecraftClass.owner == owner
		spacecraftClass.operator == operator
		spacecraftClass.affiliation == affiliation
		spacecraftClass.spacecraftTypes.contains spacecraftType1
		spacecraftClass.spacecraftTypes.contains spacecraftType2
		spacecraftClass.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClass.warpCapable == WARP_CAPABLE
		spacecraftClass.alternateReality == ALTERNATE_REALITY
		spacecraftClass.activeFrom == ACTIVE_FROM
		spacecraftClass.activeTo == ACTIVE_TO
	}

}
