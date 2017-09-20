package com.cezarykluczynski.stapi.etl.title.creation.processor.list

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class TitleListSectionProcessorTest extends Specification {

	private static final String PAGE_SECTION_TEXT = '<i>Beek</i>'
	private static final String ORGANIZATION = 'ORGANIZATION'
	private static final String NAME = "Beek (${ORGANIZATION})"
	private static final String NAME_MIRROR = "Beek (${ORGANIZATION}, mirror)"
	private static final String UID = 'UID'
	private static final Long PAGE_ID = 75

	private PageBindingService pageBindingServiceMock

	private PageRepository pageRepositoryMock

	private UidGenerator uidGeneratorMock

	private TitleRepository titleRepositoryMock

	private TitleListSectionProcessor titleListSectionProcessor

	void setup() {
		pageBindingServiceMock = Mock()
		pageRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		titleRepositoryMock = Mock()
		titleListSectionProcessor = new TitleListSectionProcessor(pageBindingServiceMock, pageRepositoryMock, uidGeneratorMock, titleRepositoryMock)
	}

	void "creates Title for index 0"() {
		given:
		Page page = new Page()
		PageSection pageSection = new PageSection(text: PAGE_SECTION_TEXT)
		ModelPage modelPage = new ModelPage()
		Integer index = 0

		when:
		titleListSectionProcessor.process(page, pageSection, ORGANIZATION, index)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, index) >> UID
		1 * titleRepositoryMock.save(_ as Title) >> { Title title ->
			assert title.name == NAME
			assert title.page == modelPage
			assert title.militaryRank
			assert !title.religiousTitle
			assert !title.position
			assert !title.fleetRank
			assert !title.mirror
			title
		}
		0 * _
	}

	void "creates Title for index higher than 0"() {
		given:
		Page page = new Page(pageId: PAGE_ID)
		PageSection pageSection = new PageSection(text: PAGE_SECTION_TEXT)
		ModelPage modelPage = new ModelPage()
		Integer index = 1

		when:
		titleListSectionProcessor.process(page, pageSection, ORGANIZATION, index)

		then:
		1 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.of(modelPage)
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, index) >> UID
		1 * titleRepositoryMock.save(_ as Title) >> { Title title ->
			title.name == NAME
			title.page == modelPage
			title.militaryRank
			!title.religiousTitle
			!title.position
			!title.fleetRank
			!title.mirror
			title
		}
		0 * _
	}

	void "does not create title when page section should be filtered out"() {
		given:
		Page page = new Page()
		PageSection pageSection = new PageSection(text: RandomUtil.randomItem(TitleListSectionProcessor.PAGE_SECTIONS_TO_FILTER_OUT))
		ModelPage modelPage = new ModelPage()
		Integer index = 0

		when:
		titleListSectionProcessor.process(page, pageSection, ORGANIZATION, index)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		0 * _
	}

	void "creates Title for mirror universe"() {
		given:
		Page page = new Page(title: 'Starfleet ranks (mirror)')
		PageSection pageSection = new PageSection(text: PAGE_SECTION_TEXT)
		ModelPage modelPage = new ModelPage()
		Integer index = 0

		when:
		titleListSectionProcessor.process(page, pageSection, ORGANIZATION, index)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, index) >> UID
		1 * titleRepositoryMock.save(_ as Title) >> { Title title ->
			title.name == NAME_MIRROR
			title.page == modelPage
			title.militaryRank
			!title.religiousTitle
			!title.position
			!title.fleetRank
			title.mirror
			title
		}
		0 * _
	}

}
