package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFull
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import org.mapstruct.factory.Mappers

class MagazineFullRestMapperTest extends AbstractMagazineMapperTest {

	private MagazineFullRestMapper magazineFullRestMapper

	void setup() {
		magazineFullRestMapper = Mappers.getMapper(MagazineFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Magazine magazine = createMagazine()

		when:
		MagazineFull magazineFull = magazineFullRestMapper.mapFull(magazine)

		then:
		magazineFull.uid == UID
		magazineFull.title == TITLE
		magazineFull.publishedYear == PUBLISHED_YEAR
		magazineFull.publishedMonth == PUBLISHED_MONTH
		magazineFull.publishedDay == PUBLISHED_DAY
		magazineFull.coverYear == COVER_YEAR
		magazineFull.coverMonth == COVER_MONTH
		magazineFull.coverDay == COVER_DAY
		magazineFull.numberOfPages == NUMBER_OF_PAGES
		magazineFull.issueNumber == ISSUE_NUMBER
		magazineFull.magazineSeries.size() == magazine.magazineSeries.size()
		magazineFull.editors.size() == magazine.editors.size()
		magazineFull.publishers.size() == magazine.publishers.size()
	}

}
