package com.cezarykluczynski.stapi.server.magazine_series.query

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MagazineSeriesSoapQueryTest extends Specification {

	private MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapperMock

	private MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private MagazineSeriesRepository magazineSeriesRepositoryMock

	private MagazineSeriesSoapQuery magazineSeriesSoapQuery

	void setup() {
		magazineSeriesBaseSoapMapperMock = Mock()
		magazineSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		magazineSeriesRepositoryMock = Mock()
		magazineSeriesSoapQuery = new MagazineSeriesSoapQuery(magazineSeriesBaseSoapMapperMock, magazineSeriesFullSoapMapperMock, pageMapperMock,
				magazineSeriesRepositoryMock)
	}

	void "maps MagazineSeriesBaseRequest to MagazineSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		MagazineSeriesBaseRequest magazineSeriesRequest = Mock()
		magazineSeriesRequest.page >> requestPage
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineSeriesSoapQuery.query(magazineSeriesRequest)

		then:
		1 * magazineSeriesBaseSoapMapperMock.mapBase(magazineSeriesRequest) >> magazineSeriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * magazineSeriesRepositoryMock.findMatching(magazineSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps MagazineSeriesFullRequest to MagazineSeriesRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MagazineSeriesFullRequest magazineSeriesRequest = Mock()
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineSeriesSoapQuery.query(magazineSeriesRequest)

		then:
		1 * magazineSeriesFullSoapMapperMock.mapFull(magazineSeriesRequest) >> magazineSeriesRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * magazineSeriesRepositoryMock.findMatching(magazineSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
