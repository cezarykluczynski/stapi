package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseHeader
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoReleaseHeaderSoapMapperTest extends AbstractVideoReleaseMapperTest {

	private VideoReleaseHeaderSoapMapper videoReleaseHeaderSoapMapper

	void setup() {
		videoReleaseHeaderSoapMapper = Mappers.getMapper(VideoReleaseHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		VideoRelease videoRelease = new VideoRelease(
				uid: UID,
				title: TITLE)

		when:
		VideoReleaseHeader videoReleaseHeader = videoReleaseHeaderSoapMapper.map(Lists.newArrayList(videoRelease))[0]

		then:
		videoReleaseHeader.uid == UID
		videoReleaseHeader.title == TITLE
	}

}
