package com.cezarykluczynski.stapi.etl.weapon.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class WeaponProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private WeaponPageProcessor weaponPageProcessorMock

	private WeaponProcessor weaponProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		weaponPageProcessorMock = Mock()
		weaponProcessor = new WeaponProcessor(pageHeaderProcessorMock, weaponPageProcessorMock)
	}

	void "converts PageHeader to Weapon"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Weapon weapon = new Weapon()

		when:
		Weapon weaponOutput = weaponProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * weaponPageProcessorMock.process(page) >> weapon

		then: 'last processor output is returned'
		weaponOutput == weapon
	}

}
