package com.cezarykluczynski.stapi.server.magazine.query

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MagazineSoapQueryTest extends Specification {

	private MagazineBaseSoapMapper magazineBaseSoapMapperMock

	private MagazineFullSoapMapper magazineFullSoapMapperMock

	private PageMapper pageMapperMock

	private MagazineRepository magazineRepositoryMock

	private MagazineSoapQuery magazineSoapQuery

	void setup() {
		magazineBaseSoapMapperMock = Mock()
		magazineFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		magazineRepositoryMock = Mock()
		magazineSoapQuery = new MagazineSoapQuery(magazineBaseSoapMapperMock, magazineFullSoapMapperMock, pageMapperMock, magazineRepositoryMock)
	}

	void "maps MagazineBaseRequest to MagazineRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		MagazineBaseRequest magazineRequest = Mock()
		magazineRequest.page >> requestPage
		MagazineRequestDTO magazineRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineSoapQuery.query(magazineRequest)

		then:
		1 * magazineBaseSoapMapperMock.mapBase(magazineRequest) >> magazineRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * magazineRepositoryMock.findMatching(magazineRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps MagazineFullRequest to MagazineRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MagazineFullRequest magazineRequest = Mock()
		MagazineRequestDTO magazineRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineSoapQuery.query(magazineRequest)

		then:
		1 * magazineFullSoapMapperMock.mapFull(magazineRequest) >> magazineRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * magazineRepositoryMock.findMatching(magazineRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
