package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class StarshipClassTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String OWNER = 'OWNER'
	private static final String OPERATOR = 'OPERATOR'
	private static final String AFFILIATION = 'AFFILIATION'
	private static final String TYPE = 'TYPE'
	private static final String ARMAMENT = 'ARMAMENT'
	private static final String DEFENSES = 'DEFENSES'

	private StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessorMock

	private StarshipClassTemplatePartOrganizationsEnrichingProcessor starshipClassTemplatePartOrganizationsEnrichingProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor

	void setup() {
		starshipClassSpacecraftTypeProcessorMock = Mock()
		starshipClassTemplatePartOrganizationsEnrichingProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		starshipClassTemplateRelationsEnrichingProcessor = new StarshipClassTemplateRelationsEnrichingProcessor(
				starshipClassSpacecraftTypeProcessorMock, starshipClassTemplatePartOrganizationsEnrichingProcessorMock,
				wikitextToEntitiesProcessorMock)
	}

	void "when owner part is found, StarshipClassTemplatePartOrganizationsEnrichingProcessor is used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.OWNER,
				value: OWNER)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(part))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * starshipClassTemplatePartOrganizationsEnrichingProcessorMock.enrich(EnrichablePair.of(part, starshipClassTemplate))
		0 * _
	}

	void "when operator part is found, StarshipClassTemplatePartOrganizationsEnrichingProcessor is used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.OPERATOR,
				value: OPERATOR)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(part))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * starshipClassTemplatePartOrganizationsEnrichingProcessorMock.enrich(EnrichablePair.of(part, starshipClassTemplate))
		0 * _
	}

	void "when affiliation part is found, StarshipClassTemplatePartOrganizationsEnrichingProcessor is used"() {
		given:
		Template.Part part = new Template.Part(
				key: StarshipClassTemplateParameter.AFFILIATION,
				value: AFFILIATION)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(part))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, starshipClassTemplate))

		then:
		1 * starshipClassTemplatePartOrganizationsEnrichingProcessorMock.enrich(EnrichablePair.of(part, starshipClassTemplate))
		0 * _
	}

	void "when type part is found, StarshipClassActivityPeriodProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.TYPE,
				value: TYPE)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		SpacecraftType spacecraftType1 = Mock()
		SpacecraftType spacecraftType2 = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * starshipClassSpacecraftTypeProcessorMock.process(TYPE) >> Sets.newHashSet(spacecraftType1, spacecraftType2)
		0 * _
		starshipClassTemplate.spacecraftTypes.contains spacecraftType1
		starshipClassTemplate.spacecraftTypes.contains spacecraftType2
	}

	void "when armament part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.ARMAMENT,
				value: ARMAMENT)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Weapon weapon1 = Mock()
		Weapon weapon2 = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findWeapons(ARMAMENT) >> [weapon1, weapon2]
		0 * _
		starshipClassTemplate.armaments.contains weapon1
		starshipClassTemplate.armaments.contains weapon2
	}

	void "when defenses part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.DEFENSES,
				value: DEFENSES)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		Technology technology1 = Mock()
		Technology technology2 = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findTechnology(DEFENSES) >> [technology1, technology2]
		0 * _
		starshipClassTemplate.defenses.contains technology1
		starshipClassTemplate.defenses.contains technology2
	}

}
