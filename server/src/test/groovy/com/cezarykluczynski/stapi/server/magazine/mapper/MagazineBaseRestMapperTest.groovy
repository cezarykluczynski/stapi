package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBase
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineBaseRestMapperTest extends AbstractMagazineMapperTest {

	private MagazineBaseRestMapper magazineBaseRestMapper

	void setup() {
		magazineBaseRestMapper = Mappers.getMapper(MagazineBaseRestMapper)
	}

	void "maps MagazineRestBeanParams to MagazineRequestDTO"() {
		given:
		MagazineRestBeanParams magazineRestBeanParams = new MagazineRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO)

		when:
		MagazineRequestDTO magazineRequestDTO = magazineBaseRestMapper.mapBase magazineRestBeanParams

		then:
		magazineRequestDTO.uid == UID
		magazineRequestDTO.title == TITLE
		magazineRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		magazineRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Magazine magazine = createMagazine()

		when:
		MagazineBase magazineBase = magazineBaseRestMapper.mapBase(Lists.newArrayList(magazine))[0]

		then:
		magazineBase.uid == UID
		magazineBase.title == TITLE
		magazineBase.publishedYear == PUBLISHED_YEAR
		magazineBase.publishedMonth == PUBLISHED_MONTH
		magazineBase.publishedDay == PUBLISHED_DAY
		magazineBase.coverYear == COVER_YEAR
		magazineBase.coverMonth == COVER_MONTH
		magazineBase.coverDay == COVER_DAY
		magazineBase.numberOfPages == NUMBER_OF_PAGES
		magazineBase.issueNumber == ISSUE_NUMBER
	}

}
