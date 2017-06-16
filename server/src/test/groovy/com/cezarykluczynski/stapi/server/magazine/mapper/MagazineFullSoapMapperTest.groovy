package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MagazineFull
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import org.mapstruct.factory.Mappers

class MagazineFullSoapMapperTest extends AbstractMagazineMapperTest {

	private MagazineFullSoapMapper magazineFullSoapMapper

	void setup() {
		magazineFullSoapMapper = Mappers.getMapper(MagazineFullSoapMapper)
	}

	void "maps SOAP MagazineFullRequest to MagazineBaseRequestDTO"() {
		given:
		MagazineFullRequest magazineFullRequest = new MagazineFullRequest(uid: UID)

		when:
		MagazineRequestDTO magazineRequestDTO = magazineFullSoapMapper.mapFull magazineFullRequest

		then:
		magazineRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Magazine magazine = createMagazine()

		when:
		MagazineFull magazineFull = magazineFullSoapMapper.mapFull(magazine)

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
