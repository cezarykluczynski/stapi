package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
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
		Organization owner1 = Mock()
		Organization owner2 = Mock()
		Organization operator1 = Mock()
		Organization operator2 = Mock()
		Organization affiliation1 = Mock()
		Organization affiliation2 = Mock()
		Weapon weapon1 = Mock()
		Weapon weapon2 = Mock()
		Technology technology1 = Mock()
		Technology technology2 = Mock()
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate(
				name: NAME,
				page: page,
				species: species,
				owners: Sets.newHashSet(owner1, owner2),
				operators: Sets.newHashSet(operator1, operator2),
				affiliations: Sets.newHashSet(affiliation1, affiliation2),
				armaments: Sets.newHashSet(weapon1, weapon2),
				defenses: Sets.newHashSet(technology1, technology2),
				spacecraftTypes: Sets.newHashSet(spacecraftType1, spacecraftType2),
				numberOfDecks: NUMBER_OF_DECKS,
				crew: CREW,
				warpCapable: WARP_CAPABLE,
				mirror: MIRROR,
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
		spacecraftClass.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClass.crew == CREW
		spacecraftClass.warpCapable == WARP_CAPABLE
		spacecraftClass.mirror == MIRROR
		spacecraftClass.alternateReality == ALTERNATE_REALITY
		spacecraftClass.activeFrom == ACTIVE_FROM
		spacecraftClass.activeTo == ACTIVE_TO
		spacecraftClass.species == species
		spacecraftClass.owners.contains owner1
		spacecraftClass.owners.contains owner2
		spacecraftClass.operators.contains operator1
		spacecraftClass.operators.contains operator2
		spacecraftClass.affiliations.contains affiliation1
		spacecraftClass.affiliations.contains affiliation2
		spacecraftClass.armaments.contains weapon1
		spacecraftClass.armaments.contains weapon2
		spacecraftClass.defenses.contains technology1
		spacecraftClass.defenses.contains technology2
		spacecraftClass.spacecraftTypes.contains spacecraftType1
		spacecraftClass.spacecraftTypes.contains spacecraftType2
	}

}
