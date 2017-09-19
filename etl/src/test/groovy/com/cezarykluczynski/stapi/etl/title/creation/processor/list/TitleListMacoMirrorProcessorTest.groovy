package com.cezarykluczynski.stapi.etl.title.creation.processor.list

import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class TitleListMacoMirrorProcessorTest extends Specification {

	private static final Long PAGE_ID = 75
	private static final String UID_1 = 'UID_1'
	private static final String UID_2 = 'UID_2'
	private static final String UID_3 = 'UID_3'
	private static final String UID_4 = 'UID_4'

	private PageRepository pageRepositoryMock

	private UidGenerator uidGeneratorMock

	private TitleRepository titleRepositoryMock

	private TitleListMacoMirrorProcessor titleListMacoMirrorProcessor

	void setup() {
		pageRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		titleRepositoryMock = Mock()
		titleListMacoMirrorProcessor = new TitleListMacoMirrorProcessor(pageRepositoryMock, uidGeneratorMock, titleRepositoryMock)
	}

	void "produces MACO ranks"() {
		given:
		Page page = new Page(pageId: PAGE_ID)
		ModelPage modelPage = new ModelPage()
		int index = 8

		when:
		List<Title> titleList = titleListMacoMirrorProcessor.produceAll(page, index)

		then:
		4 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.of(modelPage)
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, 8) >> UID_1
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, 18) >> UID_2
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, 28) >> UID_3
		1 * uidGeneratorMock.generateForTitleListItem(modelPage, 38) >> UID_4
		4 * titleRepositoryMock.save(_ as Title) >> { Title title ->
			assert title.name.contains('(MACO, mirror)')
			assert title.page == modelPage
			assert title.uid != null
			assert title.militaryRank
			assert !title.religiousTitle
			assert !title.position
			assert !title.fleetRank
			assert title.mirror
			title
		}
		0 * _
		titleList[0].uid == UID_1
		titleList[0].name.contains TitleListMacoMirrorProcessor.MACO_RANKS_MIRROR[0]
		titleList[1].uid == UID_2
		titleList[1].name.contains TitleListMacoMirrorProcessor.MACO_RANKS_MIRROR[1]
		titleList[2].uid == UID_3
		titleList[2].name.contains TitleListMacoMirrorProcessor.MACO_RANKS_MIRROR[2]
		titleList[3].uid == UID_4
		titleList[3].name.contains TitleListMacoMirrorProcessor.MACO_RANKS_MIRROR[3]
	}

}
