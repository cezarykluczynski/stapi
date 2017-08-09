package com.cezarykluczynski.stapi.server.soundtrack.query

import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SoundtrackRestQueryTest extends Specification {

	private SoundtrackBaseRestMapper soundtrackBaseRestMapperMock

	private PageMapper pageMapperMock

	private SoundtrackRepository soundtrackRepositoryMock

	private SoundtrackRestQuery soundtrackRestQuery

	void setup() {
		soundtrackBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		soundtrackRepositoryMock = Mock()
		soundtrackRestQuery = new SoundtrackRestQuery(soundtrackBaseRestMapperMock, pageMapperMock, soundtrackRepositoryMock)
	}

	void "maps SoundtrackRestBeanParams to SoundtrackRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SoundtrackRestBeanParams soundtrackRestBeanParams = Mock()
		SoundtrackRequestDTO soundtrackRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = soundtrackRestQuery.query(soundtrackRestBeanParams)

		then:
		1 * soundtrackBaseRestMapperMock.mapBase(soundtrackRestBeanParams) >> soundtrackRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(soundtrackRestBeanParams) >> pageRequest
		1 * soundtrackRepositoryMock.findMatching(soundtrackRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
