package com.cezarykluczynski.stapi.server.soundtrack.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper
import com.cezarykluczynski.stapi.server.soundtrack.query.SoundtrackRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SoundtrackRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SoundtrackRestQuery soundtrackRestQueryBuilderMock

	private SoundtrackBaseRestMapper soundtrackBaseRestMapperMock

	private SoundtrackFullRestMapper soundtrackFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SoundtrackRestReader soundtrackRestReader

	void setup() {
		soundtrackRestQueryBuilderMock = Mock()
		soundtrackBaseRestMapperMock = Mock()
		soundtrackFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		soundtrackRestReader = new SoundtrackRestReader(soundtrackRestQueryBuilderMock, soundtrackBaseRestMapperMock, soundtrackFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SoundtrackBase soundtrackBase = Mock()
		Soundtrack soundtrack = Mock()
		SoundtrackRestBeanParams soundtrackRestBeanParams = Mock()
		List<SoundtrackBase> restSoundtrackList = Lists.newArrayList(soundtrackBase)
		List<Soundtrack> soundtrackList = Lists.newArrayList(soundtrack)
		Page<Soundtrack> soundtrackPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SoundtrackBaseResponse soundtrackResponseOutput = soundtrackRestReader.readBase(soundtrackRestBeanParams)

		then:
		1 * soundtrackRestQueryBuilderMock.query(soundtrackRestBeanParams) >> soundtrackPage
		1 * pageMapperMock.fromPageToRestResponsePage(soundtrackPage) >> responsePage
		1 * soundtrackRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * soundtrackPage.content >> soundtrackList
		1 * soundtrackBaseRestMapperMock.mapBase(soundtrackList) >> restSoundtrackList
		0 * _
		soundtrackResponseOutput.soundtracks == restSoundtrackList
		soundtrackResponseOutput.page == responsePage
		soundtrackResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SoundtrackFull soundtrackFull = Mock()
		Soundtrack soundtrack = Mock()
		List<Soundtrack> soundtrackList = Lists.newArrayList(soundtrack)
		Page<Soundtrack> soundtrackPage = Mock()

		when:
		SoundtrackFullResponse soundtrackResponseOutput = soundtrackRestReader.readFull(UID)

		then:
		1 * soundtrackRestQueryBuilderMock.query(_ as SoundtrackRestBeanParams) >> { SoundtrackRestBeanParams soundtrackRestBeanParams ->
			assert soundtrackRestBeanParams.uid == UID
			soundtrackPage
		}
		1 * soundtrackPage.content >> soundtrackList
		1 * soundtrackFullRestMapperMock.mapFull(soundtrack) >> soundtrackFull
		0 * _
		soundtrackResponseOutput.soundtrack == soundtrackFull
	}

	void "requires UID in full request"() {
		when:
		soundtrackRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
