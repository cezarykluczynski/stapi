package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeriesSoapQueryTest extends Specification {

	private SeriesBaseSoapMapper seriesBaseSoapMapperMock

	private SeriesFullSoapMapper seriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SeriesRepository seriesRepositoryMock

	private SeriesSoapQuery seriesSoapQuery

	void setup() {
		seriesBaseSoapMapperMock = Mock()
		seriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		seriesRepositoryMock = Mock()
		seriesSoapQuery = new SeriesSoapQuery(seriesBaseSoapMapperMock, seriesFullSoapMapperMock, pageMapperMock, seriesRepositoryMock)
	}

	void "maps SeriesBaseRequest to SeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SeriesBaseRequest seriesRequest = Mock()
		seriesRequest.page >> requestPage
		SeriesRequestDTO seriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seriesSoapQuery.query(seriesRequest)

		then:
		1 * seriesBaseSoapMapperMock.mapBase(seriesRequest) >> seriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * seriesRepositoryMock.findMatching(seriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SeriesFullRequest to SeriesRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SeriesFullRequest seriesRequest = Mock()
		SeriesRequestDTO seriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seriesSoapQuery.query(seriesRequest)

		then:
		1 * seriesFullSoapMapperMock.mapFull(seriesRequest) >> seriesRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * seriesRepositoryMock.findMatching(seriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
