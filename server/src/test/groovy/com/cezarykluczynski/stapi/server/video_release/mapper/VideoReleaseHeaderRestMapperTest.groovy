package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseHeader
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoReleaseHeaderRestMapperTest extends AbstractVideoReleaseMapperTest {

	private VideoReleaseHeaderRestMapper videoReleaseHeaderRestMapper

	void setup() {
		videoReleaseHeaderRestMapper = Mappers.getMapper(VideoReleaseHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		VideoRelease videoRelease = new VideoRelease(
				uid: UID,
				title: TITLE)

		when:
		VideoReleaseHeader videoReleaseHeader = videoReleaseHeaderRestMapper.map(Lists.newArrayList(videoRelease))[0]

		then:
		videoReleaseHeader.uid == UID
		videoReleaseHeader.title == TITLE
	}

}
