package com.cezarykluczynski.stapi.etl.weapon.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.weapon.creation.service.WeaponPageFilter
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class WeaponPageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private WeaponPageFilter weaponPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private TemplateFinder templateFinderMock

	private WeaponPageProcessor weaponPageProcessor

	void setup() {
		weaponPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		templateFinderMock = Mock()
		weaponPageProcessor = new WeaponPageProcessor(weaponPageFilterMock, pageBindingServiceMock, uidGeneratorMock,
				categoryTitlesExtractingProcessorMock, templateFinderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Weapon weapon = weaponPageProcessor.process(page)

		then:
		1 * weaponPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		weapon == null
	}

	void "page is bound"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Weapon weapon = weaponPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		weapon.page == modelPage
	}

	void "UID is generated"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)
		ModelPage modelPage = new ModelPage()

		when:
		Weapon weapon = weaponPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Weapon) >> UID
		weapon.uid == UID
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName from categories when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Weapon weapon = weaponPageProcessor.process(page)
		weapon[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(weapon) == trueBooleans

		where:
		page                                                                     | flagName           | flag  | trueBooleans
		new SourcesPage(categories: Lists.newArrayList())                        | 'handHeldWeapon'   | false | 0
		new SourcesPage(categories: createList(CategoryTitle.HAND_HELD_WEAPONS)) | 'handHeldWeapon'   | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.MIRROR_UNIVERSE))   | 'mirror'           | true  | 1
		new SourcesPage(categories: createList(CategoryTitle.ALTERNATE_REALITY)) | 'alternateReality' | true  | 1
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName from page title when page is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> Lists.newArrayList()

		expect:
		Weapon weapon = weaponPageProcessor.process(page)
		weapon[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(weapon) == trueBooleans

		where:
		page                                         | flagName             | flag  | trueBooleans
		new SourcesPage(title: '')                   | 'laserTechnology'    | false | 0
		new SourcesPage(title: 'Laser cannon')       | 'laserTechnology'    | true  | 1
		new SourcesPage(title: 'Plasma torpedo')     | 'plasmaTechnology'   | true  | 1
		new SourcesPage(title: 'Photonic disruptor') | 'photonicTechnology' | true  | 1
		new SourcesPage(title: 'Plasma charge')      | 'plasmaTechnology'   | true  | 1
		new SourcesPage(title: 'Plasma phaser')      | 'plasmaTechnology'   | true  | 2
		new SourcesPage(title: 'Plasma phaser')      | 'phaserTechnology'   | true  | 2
	}

	void "sets laserTechnology flag when LaserTechnology template is present"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Weapon weapon = weaponPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.hasTemplate(page, TemplateTitle.LASER_TECHNOLOGY) >> true
		weapon.laserTechnology
	}

	void "sets phaserTechnology flag when PhaserTechnology template is present"() {
		given:
		SourcesPage page = new SourcesPage(title: NAME)

		when:
		Weapon weapon = weaponPageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.hasTemplate(page, TemplateTitle.PHASER_TECHNOLOGY) >> true
		weapon.phaserTechnology
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
