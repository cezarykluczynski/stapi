package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeHeader
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeHeaderSoapMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeHeaderSoapMapper episodeHeaderSoapMapper

	void setup() {
		episodeHeaderSoapMapper = Mappers.getMapper(EpisodeHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Episode episode = new Episode(
				title: TITLE,
				uid: UID)

		when:
		EpisodeHeader episodeHeader = episodeHeaderSoapMapper.map(Lists.newArrayList(episode))[0]

		then:
		episodeHeader.title == TITLE
		episodeHeader.uid == UID
	}

}
