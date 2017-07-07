package com.cezarykluczynski.stapi.server.video_release.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class VideoReleaseSoapQueryTest extends Specification {

	private VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapperMock

	private VideoReleaseFullSoapMapper videoReleaseFullSoapMapperMock

	private PageMapper pageMapperMock

	private VideoReleaseRepository videoReleaseRepositoryMock

	private VideoReleaseSoapQuery videoReleaseSoapQuery

	void setup() {
		videoReleaseBaseSoapMapperMock = Mock()
		videoReleaseFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		videoReleaseRepositoryMock = Mock()
		videoReleaseSoapQuery = new VideoReleaseSoapQuery(videoReleaseBaseSoapMapperMock, videoReleaseFullSoapMapperMock, pageMapperMock,
				videoReleaseRepositoryMock)
	}

	void "maps VideoReleaseBaseRequest to VideoReleaseRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		VideoReleaseBaseRequest videoReleaseRequest = Mock()
		videoReleaseRequest.page >> requestPage
		VideoReleaseRequestDTO videoReleaseRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoReleaseSoapQuery.query(videoReleaseRequest)

		then:
		1 * videoReleaseBaseSoapMapperMock.mapBase(videoReleaseRequest) >> videoReleaseRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * videoReleaseRepositoryMock.findMatching(videoReleaseRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps VideoReleaseFullRequest to VideoReleaseRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		VideoReleaseFullRequest videoReleaseRequest = Mock()
		VideoReleaseRequestDTO videoReleaseRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoReleaseSoapQuery.query(videoReleaseRequest)

		then:
		1 * videoReleaseFullSoapMapperMock.mapFull(videoReleaseRequest) >> videoReleaseRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * videoReleaseRepositoryMock.findMatching(videoReleaseRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
