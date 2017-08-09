package com.cezarykluczynski.stapi.server.soundtrack.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBase
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFull
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper
import com.cezarykluczynski.stapi.server.soundtrack.query.SoundtrackSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SoundtrackSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private SoundtrackSoapQuery soundtrackSoapQueryBuilderMock

	private SoundtrackBaseSoapMapper soundtrackBaseSoapMapperMock

	private SoundtrackFullSoapMapper soundtrackFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SoundtrackSoapReader soundtrackSoapReader

	void setup() {
		soundtrackSoapQueryBuilderMock = Mock()
		soundtrackBaseSoapMapperMock = Mock()
		soundtrackFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		soundtrackSoapReader = new SoundtrackSoapReader(soundtrackSoapQueryBuilderMock, soundtrackBaseSoapMapperMock, soundtrackFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Soundtrack> soundtrackList = Lists.newArrayList()
		Page<Soundtrack> soundtrackPage = Mock()
		List<SoundtrackBase> soapSoundtrackList = Lists.newArrayList(new SoundtrackBase(uid: UID))
		SoundtrackBaseRequest soundtrackBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		SoundtrackBaseResponse soundtrackResponse = soundtrackSoapReader.readBase(soundtrackBaseRequest)

		then:
		1 * soundtrackSoapQueryBuilderMock.query(soundtrackBaseRequest) >> soundtrackPage
		1 * soundtrackPage.content >> soundtrackList
		1 * pageMapperMock.fromPageToSoapResponsePage(soundtrackPage) >> responsePage
		1 * soundtrackBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * soundtrackBaseSoapMapperMock.mapBase(soundtrackList) >> soapSoundtrackList
		0 * _
		soundtrackResponse.soundtracks[0].uid == UID
		soundtrackResponse.page == responsePage
		soundtrackResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SoundtrackFull soundtrackFull = new SoundtrackFull(uid: UID)
		Soundtrack soundtrack = Mock()
		Page<Soundtrack> soundtrackPage = Mock()
		SoundtrackFullRequest soundtrackFullRequest = new SoundtrackFullRequest(uid: UID)

		when:
		SoundtrackFullResponse soundtrackFullResponse = soundtrackSoapReader.readFull(soundtrackFullRequest)

		then:
		1 * soundtrackSoapQueryBuilderMock.query(soundtrackFullRequest) >> soundtrackPage
		1 * soundtrackPage.content >> Lists.newArrayList(soundtrack)
		1 * soundtrackFullSoapMapperMock.mapFull(soundtrack) >> soundtrackFull
		0 * _
		soundtrackFullResponse.soundtrack.uid == UID
	}

	void "requires UID in full request"() {
		given:
		SoundtrackFullRequest soundtrackFullRequest = Mock()

		when:
		soundtrackSoapReader.readFull(soundtrackFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
