package com.cezarykluczynski.stapi.server.magazine.reader

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBase
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFull
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper
import com.cezarykluczynski.stapi.server.magazine.query.MagazineSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MagazineSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private MagazineSoapQuery magazineSoapQueryBuilderMock

	private MagazineBaseSoapMapper magazineBaseSoapMapperMock

	private MagazineFullSoapMapper magazineFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MagazineSoapReader magazineSoapReader

	void setup() {
		magazineSoapQueryBuilderMock = Mock()
		magazineBaseSoapMapperMock = Mock()
		magazineFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		magazineSoapReader = new MagazineSoapReader(magazineSoapQueryBuilderMock, magazineBaseSoapMapperMock, magazineFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Magazine> magazineList = Lists.newArrayList()
		Page<Magazine> magazinePage = Mock()
		List<MagazineBase> soapMagazineList = Lists.newArrayList(new MagazineBase(uid: UID))
		MagazineBaseRequest magazineBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		MagazineBaseResponse magazineResponse = magazineSoapReader.readBase(magazineBaseRequest)

		then:
		1 * magazineSoapQueryBuilderMock.query(magazineBaseRequest) >> magazinePage
		1 * magazinePage.content >> magazineList
		1 * pageMapperMock.fromPageToSoapResponsePage(magazinePage) >> responsePage
		1 * magazineBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * magazineBaseSoapMapperMock.mapBase(magazineList) >> soapMagazineList
		0 * _
		magazineResponse.magazine[0].uid == UID
		magazineResponse.page == responsePage
		magazineResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineFull magazineFull = new MagazineFull(uid: UID)
		Magazine magazine = Mock()
		Page<Magazine> magazinePage = Mock()
		MagazineFullRequest magazineFullRequest = new MagazineFullRequest(uid: UID)

		when:
		MagazineFullResponse magazineFullResponse = magazineSoapReader.readFull(magazineFullRequest)

		then:
		1 * magazineSoapQueryBuilderMock.query(magazineFullRequest) >> magazinePage
		1 * magazinePage.content >> Lists.newArrayList(magazine)
		1 * magazineFullSoapMapperMock.mapFull(magazine) >> magazineFull
		0 * _
		magazineFullResponse.magazine.uid == UID
	}

	void "requires UID in full request"() {
		given:
		MagazineFullRequest magazineFullRequest = Mock()

		when:
		magazineSoapReader.readFull(magazineFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
