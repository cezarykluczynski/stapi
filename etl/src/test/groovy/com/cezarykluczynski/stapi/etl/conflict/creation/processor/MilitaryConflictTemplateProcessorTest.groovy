package com.cezarykluczynski.stapi.etl.conflict.creation.processor

import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.util.AbstractConflictTest
import com.google.common.collect.Sets

class MilitaryConflictTemplateProcessorTest extends AbstractConflictTest {

	private UidGenerator uidGeneratorMock

	private MilitaryConflictTemplateProcessor militaryConflictTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		militaryConflictTemplateProcessor = new MilitaryConflictTemplateProcessor(uidGeneratorMock)
	}

	void "converts MilitaryConflictTemplate to Conflict"() {
		given:
		Location location1 = Mock()
		Location location2 = Mock()
		Organization firstSideBelligerent1 = Mock()
		Organization firstSideBelligerent2 = Mock()
		Organization secondSideBelligerent1 = Mock()
		Organization secondSideBelligerent2 = Mock()
		Character firstSideCommander1 = Mock()
		Character firstSideCommander2 = Mock()
		Character secondSideCommander1 = Mock()
		Character secondSideCommander2 = Mock()
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate(
				page: page,
				name: NAME,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				earthConflict: EARTH_CONFLICT,
				federationWar: FEDERATION_WAR,
				klingonWar: KLINGON_WAR,
				dominionWarBattle: DOMINION_WAR_BATTLE,
				alternateReality: ALTERNATE_REALITY,
				locations: Sets.newHashSet(location1, location2),
				firstSideBelligerents: Sets.newHashSet(firstSideBelligerent1, firstSideBelligerent2),
				secondSideBelligerents: Sets.newHashSet(secondSideBelligerent1, secondSideBelligerent2),
				firstSideCommanders: Sets.newHashSet(firstSideCommander1, firstSideCommander2),
				secondSideCommanders: Sets.newHashSet(secondSideCommander1, secondSideCommander2))

		when:
		Conflict conflict = militaryConflictTemplateProcessor.process(militaryConflictTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Conflict) >> UID
		0 * _
		conflict.uid == UID
		conflict.page == page
		conflict.name == NAME
		conflict.yearFrom == YEAR_FROM
		conflict.yearTo == YEAR_TO
		conflict.earthConflict == EARTH_CONFLICT
		conflict.federationWar == FEDERATION_WAR
		conflict.klingonWar == KLINGON_WAR
		conflict.dominionWarBattle == DOMINION_WAR_BATTLE
		conflict.alternateReality == ALTERNATE_REALITY
		conflict.locations.contains location1
		conflict.locations.contains location2
		conflict.firstSideBelligerents.contains firstSideBelligerent1
		conflict.firstSideBelligerents.contains firstSideBelligerent2
		conflict.secondSideBelligerents.contains secondSideBelligerent1
		conflict.secondSideBelligerents.contains secondSideBelligerent2
		conflict.firstSideCommanders.contains firstSideCommander1
		conflict.firstSideCommanders.contains firstSideCommander2
		conflict.secondSideCommanders.contains secondSideCommander1
		conflict.secondSideCommanders.contains secondSideCommander2
	}

}
