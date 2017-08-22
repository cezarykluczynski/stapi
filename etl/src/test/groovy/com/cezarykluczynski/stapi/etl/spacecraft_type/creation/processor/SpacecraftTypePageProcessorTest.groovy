package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftTypePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String UID = 'UID'

	private CategorySortingService categorySortingServiceMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private SpacecraftTypePageProcessor spacecraftTypePageProcessor

	void setup() {
		categorySortingServiceMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		spacecraftTypePageProcessor = new SpacecraftTypePageProcessor(categorySortingServiceMock, pageBindingServiceMock, uidGeneratorMock)
	}

	void "returns null when page is sorted on top of 'Spacecraft classifications' category"() {
		given:
		Page page = new Page()

		when:
		SpacecraftType spacecraftType = spacecraftTypePageProcessor.process(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS)) >> true
		0 * _
		spacecraftType == null
	}

	void "returns entity when page is not sorted on top of 'Spacecraft classifications' category"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = new ModelPage()

		when:
		SpacecraftType spacecraftType = spacecraftTypePageProcessor.process(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS)) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, SpacecraftType) >> UID
		0 * _
		spacecraftType.name == TITLE
		spacecraftType.page == modelPage
		spacecraftType.uid == UID
	}

}
