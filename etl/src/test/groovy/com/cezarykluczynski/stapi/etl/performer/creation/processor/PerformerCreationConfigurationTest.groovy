package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.wiki.api.CategoryApi
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class PerformerCreationConfigurationTest extends Specification {

	private static final String TITLE_PERFORMERS = 'TITLE_PERFORMERS'
	private static final String TITLE_ANIMAL_PERFORMERS = "TITLE_ANIMAL_PERFORMERS"
	private static final String TITLE_DIS_PERFORMERS = "TITLE_DIS_PERFORMERS"
	private static final String TITLE_DS9_PERFORMERS = "TITLE_DS9_PERFORMERS"
	private static final String TITLE_ENT_PERFORMERS = "TITLE_ENT_PERFORMERS"
	private static final String TITLE_FILM_PERFORMERS = "TITLE_FILM_PERFORMERS"
	private static final String TITLE_STAND_INS = "TITLE_STAND_INS"
	private static final String TITLE_STUNT_PERFORMERS = "TITLE_STUNT_PERFORMERS"
	private static final String TITLE_TAS_PERFORMERS = "TITLE_TAS_PERFORMERS"
	private static final String TITLE_TNG_PERFORMERS = "TITLE_TNG_PERFORMERS"
	private static final String TITLE_TOS_PERFORMERS = "TITLE_TOS_PERFORMERS"
	private static final String TITLE_VIDEO_GAME_PERFORMERS = "TITLE_VIDEO_GAME_PERFORMERS"
	private static final String TITLE_VOICE_PERFORMERS = "TITLE_VOICE_PERFORMERS"
	private static final String TITLE_VOY_PERFORMERS = "TITLE_VOY_PERFORMERS"

	private CategoryApi categoryApiMock

	private PerformerCreationConfiguration performerCreationConfiguration

	def setup() {
		categoryApiMock = Mock(CategoryApi)
		performerCreationConfiguration = new PerformerCreationConfiguration(
				categoryApi: categoryApiMock)
	}

	def "PerformerReader is created"() {
		when:
		PerformerReader performerReader = performerCreationConfiguration.performerReader()
		List<String> categoryHeaderTitleList = readerToList(performerReader)

		then:
		1 * categoryApiMock.getPages(CategoryName.PERFORMERS) >> createListWithPageHeaderTitle(TITLE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ANIMAL_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_ANIMAL_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DIS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_DIS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DS9_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_DS9_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ENT_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_ENT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.FILM_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_FILM_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.STAND_INS) >> createListWithPageHeaderTitle(TITLE_STAND_INS)
		1 * categoryApiMock.getPages(CategoryName.STUNT_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_STUNT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TAS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TAS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TNG_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TNG_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TOS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TOS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VIDEO_GAME_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VIDEO_GAME_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOICE_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VOICE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOY_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VOY_PERFORMERS)
		categoryHeaderTitleList.contains TITLE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ANIMAL_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DIS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DS9_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ENT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_FILM_PERFORMERS
		categoryHeaderTitleList.contains TITLE_STAND_INS
		categoryHeaderTitleList.contains TITLE_STUNT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TAS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TNG_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TOS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VIDEO_GAME_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOICE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOY_PERFORMERS
	}

	private static List<PageHeader> createListWithPageHeaderTitle(String title) {
		return Lists.newArrayList(PageHeader.builder().title(title).build())
	}

	private static List<String> readerToList(PerformerReader performerReader) {
		List<String> pageHeaderList = Lists.newArrayList()

		while(true) {
			PageHeader pageHeader = performerReader.read()

			if (pageHeader == null) {
				break
			}

			pageHeaderList.add pageHeader.title
		}

		return pageHeaderList
	}

}
