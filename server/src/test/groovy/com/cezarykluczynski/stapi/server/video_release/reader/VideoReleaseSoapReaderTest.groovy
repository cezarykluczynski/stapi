package com.cezarykluczynski.stapi.server.video_release.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFull
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class VideoReleaseSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private VideoReleaseSoapQuery videoReleaseSoapQueryBuilderMock

	private VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapperMock

	private VideoReleaseFullSoapMapper videoReleaseFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private VideoReleaseSoapReader videoReleaseSoapReader

	void setup() {
		videoReleaseSoapQueryBuilderMock = Mock()
		videoReleaseBaseSoapMapperMock = Mock()
		videoReleaseFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		videoReleaseSoapReader = new VideoReleaseSoapReader(videoReleaseSoapQueryBuilderMock, videoReleaseBaseSoapMapperMock,
				videoReleaseFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<VideoRelease> videoReleaseList = Lists.newArrayList()
		Page<VideoRelease> videoReleasePage = Mock()
		List<VideoReleaseBase> soapVideoReleaseList = Lists.newArrayList(new VideoReleaseBase(uid: UID))
		VideoReleaseBaseRequest videoReleaseBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseResponse = videoReleaseSoapReader.readBase(videoReleaseBaseRequest)

		then:
		1 * videoReleaseSoapQueryBuilderMock.query(videoReleaseBaseRequest) >> videoReleasePage
		1 * videoReleasePage.content >> videoReleaseList
		1 * pageMapperMock.fromPageToSoapResponsePage(videoReleasePage) >> responsePage
		1 * videoReleaseBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * videoReleaseBaseSoapMapperMock.mapBase(videoReleaseList) >> soapVideoReleaseList
		0 * _
		videoReleaseResponse.videoReleases[0].uid == UID
		videoReleaseResponse.page == responsePage
		videoReleaseResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoReleaseFull videoReleaseFull = new VideoReleaseFull(uid: UID)
		VideoRelease videoRelease = Mock()
		Page<VideoRelease> videoReleasePage = Mock()
		VideoReleaseFullRequest videoReleaseFullRequest = new VideoReleaseFullRequest(uid: UID)

		when:
		VideoReleaseFullResponse videoReleaseFullResponse = videoReleaseSoapReader.readFull(videoReleaseFullRequest)

		then:
		1 * videoReleaseSoapQueryBuilderMock.query(videoReleaseFullRequest) >> videoReleasePage
		1 * videoReleasePage.content >> Lists.newArrayList(videoRelease)
		1 * videoReleaseFullSoapMapperMock.mapFull(videoRelease) >> videoReleaseFull
		0 * _
		videoReleaseFullResponse.videoRelease.uid == UID
	}

	void "requires UID in full request"() {
		given:
		VideoReleaseFullRequest videoReleaseFullRequest = Mock()

		when:
		videoReleaseSoapReader.readFull(videoReleaseFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
