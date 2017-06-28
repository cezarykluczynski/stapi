package com.cezarykluczynski.stapi.server.season.query

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeasonSoapQueryTest extends Specification {

	private SeasonBaseSoapMapper seasonBaseSoapMapperMock

	private SeasonFullSoapMapper seasonFullSoapMapperMock

	private PageMapper pageMapperMock

	private SeasonRepository seasonRepositoryMock

	private SeasonSoapQuery seasonSoapQuery

	void setup() {
		seasonBaseSoapMapperMock = Mock()
		seasonFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		seasonRepositoryMock = Mock()
		seasonSoapQuery = new SeasonSoapQuery(seasonBaseSoapMapperMock, seasonFullSoapMapperMock, pageMapperMock, seasonRepositoryMock)
	}

	void "maps SeasonBaseRequest to SeasonRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SeasonBaseRequest seasonRequest = Mock()
		seasonRequest.page >> requestPage
		SeasonRequestDTO seasonRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seasonSoapQuery.query(seasonRequest)

		then:
		1 * seasonBaseSoapMapperMock.mapBase(seasonRequest) >> seasonRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * seasonRepositoryMock.findMatching(seasonRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SeasonFullRequest to SeasonRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SeasonFullRequest seasonRequest = Mock()
		SeasonRequestDTO seasonRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seasonSoapQuery.query(seasonRequest)

		then:
		1 * seasonFullSoapMapperMock.mapFull(seasonRequest) >> seasonRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * seasonRepositoryMock.findMatching(seasonRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
