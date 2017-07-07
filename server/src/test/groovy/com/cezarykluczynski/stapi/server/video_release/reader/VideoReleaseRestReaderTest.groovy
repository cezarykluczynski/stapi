package com.cezarykluczynski.stapi.server.video_release.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBase
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFull
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class VideoReleaseRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private VideoReleaseRestQuery videoReleaseRestQueryBuilderMock

	private VideoReleaseBaseRestMapper videoReleaseBaseRestMapperMock

	private VideoReleaseFullRestMapper videoReleaseFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private VideoReleaseRestReader videoReleaseRestReader

	void setup() {
		videoReleaseRestQueryBuilderMock = Mock()
		videoReleaseBaseRestMapperMock = Mock()
		videoReleaseFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		videoReleaseRestReader = new VideoReleaseRestReader(videoReleaseRestQueryBuilderMock, videoReleaseBaseRestMapperMock,
				videoReleaseFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoReleaseBase videoReleaseBase = Mock()
		VideoRelease videoRelease = Mock()
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = Mock()
		List<VideoReleaseBase> restVideoReleaseList = Lists.newArrayList(videoReleaseBase)
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)
		Page<VideoRelease> videoReleasePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseResponseOutput = videoReleaseRestReader.readBase(videoReleaseRestBeanParams)

		then:
		1 * videoReleaseRestQueryBuilderMock.query(videoReleaseRestBeanParams) >> videoReleasePage
		1 * pageMapperMock.fromPageToRestResponsePage(videoReleasePage) >> responsePage
		1 * videoReleaseRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * videoReleasePage.content >> videoReleaseList
		1 * videoReleaseBaseRestMapperMock.mapBase(videoReleaseList) >> restVideoReleaseList
		0 * _
		videoReleaseResponseOutput.videoReleases == restVideoReleaseList
		videoReleaseResponseOutput.page == responsePage
		videoReleaseResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		VideoReleaseFull videoReleaseFull = Mock()
		VideoRelease videoRelease = Mock()
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)
		Page<VideoRelease> videoReleasePage = Mock()

		when:
		VideoReleaseFullResponse videoReleaseResponseOutput = videoReleaseRestReader.readFull(UID)

		then:
		1 * videoReleaseRestQueryBuilderMock.query(_ as VideoReleaseRestBeanParams) >> { VideoReleaseRestBeanParams videoReleaseRestBeanParams ->
			assert videoReleaseRestBeanParams.uid == UID
			videoReleasePage
		}
		1 * videoReleasePage.content >> videoReleaseList
		1 * videoReleaseFullRestMapperMock.mapFull(videoRelease) >> videoReleaseFull
		0 * _
		videoReleaseResponseOutput.videoRelease == videoReleaseFull
	}

	void "requires UID in full request"() {
		when:
		videoReleaseRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
