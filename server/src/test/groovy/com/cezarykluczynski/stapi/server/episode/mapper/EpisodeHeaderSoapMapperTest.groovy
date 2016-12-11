package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeHeader
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeHeaderSoapMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeHeaderSoapMapper episodeHeaderSoapMapper

	def setup() {
		episodeHeaderSoapMapper = Mappers.getMapper(EpisodeHeaderSoapMapper)
	}

	def "maps DB entity to SOAP header"() {
		given:
		Episode episode = new Episode(
				title: TITLE,
				guid: GUID)

		when:
		EpisodeHeader episodeHeader = episodeHeaderSoapMapper.map(Lists.newArrayList(episode))[0]

		then:
		episodeHeader.title == TITLE
		episodeHeader.guid == GUID
	}

}
