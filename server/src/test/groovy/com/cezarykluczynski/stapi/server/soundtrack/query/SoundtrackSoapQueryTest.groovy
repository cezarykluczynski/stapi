package com.cezarykluczynski.stapi.server.soundtrack.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SoundtrackSoapQueryTest extends Specification {

	private SoundtrackBaseSoapMapper soundtrackBaseSoapMapperMock

	private SoundtrackFullSoapMapper soundtrackFullSoapMapperMock

	private PageMapper pageMapperMock

	private SoundtrackRepository soundtrackRepositoryMock

	private SoundtrackSoapQuery soundtrackSoapQuery

	void setup() {
		soundtrackBaseSoapMapperMock = Mock()
		soundtrackFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		soundtrackRepositoryMock = Mock()
		soundtrackSoapQuery = new SoundtrackSoapQuery(soundtrackBaseSoapMapperMock, soundtrackFullSoapMapperMock, pageMapperMock,
				soundtrackRepositoryMock)
	}

	void "maps SoundtrackBaseRequest to SoundtrackRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SoundtrackBaseRequest soundtrackRequest = Mock()
		soundtrackRequest.page >> requestPage
		SoundtrackRequestDTO soundtrackRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = soundtrackSoapQuery.query(soundtrackRequest)

		then:
		1 * soundtrackBaseSoapMapperMock.mapBase(soundtrackRequest) >> soundtrackRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * soundtrackRepositoryMock.findMatching(soundtrackRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SoundtrackFullRequest to SoundtrackRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SoundtrackFullRequest soundtrackRequest = Mock()
		SoundtrackRequestDTO soundtrackRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = soundtrackSoapQuery.query(soundtrackRequest)

		then:
		1 * soundtrackFullSoapMapperMock.mapFull(soundtrackRequest) >> soundtrackRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * soundtrackRepositoryMock.findMatching(soundtrackRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
