package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBase as MagazineBase
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine as Magazine
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineBaseSoapMapperTest extends AbstractMagazineMapperTest {

	private MagazineBaseSoapMapper magazineBaseSoapMapper

	void setup() {
		magazineBaseSoapMapper = Mappers.getMapper(MagazineBaseSoapMapper)
	}

	void "maps SOAP MagazineRequest to MagazineRequestDTO"() {
		given:
		MagazineBaseRequest magazineBaseRequest = new MagazineBaseRequest(
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfPages: new IntegerRange(
						from: NUMBER_OF_PAGES_FROM,
						to: NUMBER_OF_PAGES_TO
				))

		when:
		MagazineRequestDTO magazineRequestDTO = magazineBaseSoapMapper.mapBase magazineBaseRequest

		then:
		magazineRequestDTO.title == TITLE
		magazineRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		magazineRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Magazine magazine = createMagazine()

		when:
		MagazineBase magazineBase = magazineBaseSoapMapper.mapBase(Lists.newArrayList(magazine))[0]

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
