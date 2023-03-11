package com.cezarykluczynski.stapi.server.video_release.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2Base
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2Full
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2FullResponse
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class VideoReleaseV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private VideoReleaseRestQuery videoReleaseRestQueryBuilderMock

	private VideoReleaseBaseRestMapper videoReleaseBaseRestMapperMock

	private VideoReleaseFullRestMapper videoReleaseFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private VideoReleaseV2RestReader videoReleaseV2RestReader

	void setup() {
		videoReleaseRestQueryBuilderMock = Mock()
		videoReleaseBaseRestMapperMock = Mock()
		videoReleaseFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		videoReleaseV2RestReader = new VideoReleaseV2RestReader(videoReleaseRestQueryBuilderMock, videoReleaseBaseRestMapperMock,
				videoReleaseFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoReleaseV2Base videoReleaseBase = Mock()
		VideoRelease videoRelease = Mock()
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = Mock()
		List<VideoReleaseV2Base> restVideoReleaseList = Lists.newArrayList(videoReleaseBase)
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)
		Page<VideoRelease> videoReleasePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		VideoReleaseV2BaseResponse videoReleaseV2ResponseOutput = videoReleaseV2RestReader.readBase(videoReleaseV2RestBeanParams)

		then:
		1 * videoReleaseRestQueryBuilderMock.query(videoReleaseV2RestBeanParams) >> videoReleasePage
		1 * pageMapperMock.fromPageToRestResponsePage(videoReleasePage) >> responsePage
		1 * videoReleaseV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * videoReleasePage.content >> videoReleaseList
		1 * videoReleaseBaseRestMapperMock.mapV2Base(videoReleaseList) >> restVideoReleaseList
		0 * _
		videoReleaseV2ResponseOutput.videoReleases == restVideoReleaseList
		videoReleaseV2ResponseOutput.page == responsePage
		videoReleaseV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoReleaseV2Full videoReleaseV2Full = Mock()
		VideoRelease videoRelease = Mock()
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)
		Page<VideoRelease> videoReleasePage = Mock()

		when:
		VideoReleaseV2FullResponse videoReleaseV2ResponseOutput = videoReleaseV2RestReader.readFull(UID)

		then:
		1 * videoReleaseRestQueryBuilderMock.query(_ as VideoReleaseV2RestBeanParams) >> {
				VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams ->
			assert videoReleaseV2RestBeanParams.uid == UID
			videoReleasePage
		}
		1 * videoReleasePage.content >> videoReleaseList
		1 * videoReleaseFullRestMapperMock.mapV2Full(videoRelease) >> videoReleaseV2Full
		0 * _
		videoReleaseV2ResponseOutput.videoRelease == videoReleaseV2Full
	}

	void "requires UID in full request"() {
		when:
		videoReleaseV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
