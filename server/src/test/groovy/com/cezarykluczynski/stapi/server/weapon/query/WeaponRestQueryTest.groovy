package com.cezarykluczynski.stapi.server.weapon.query

import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class WeaponRestQueryTest extends Specification {

	private WeaponBaseRestMapper weaponBaseRestMapperMock

	private PageMapper pageMapperMock

	private WeaponRepository weaponRepositoryMock

	private WeaponRestQuery weaponRestQuery

	void setup() {
		weaponBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		weaponRepositoryMock = Mock()
		weaponRestQuery = new WeaponRestQuery(weaponBaseRestMapperMock, pageMapperMock, weaponRepositoryMock)
	}

	void "maps WeaponRestBeanParams to WeaponRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		WeaponRestBeanParams weaponRestBeanParams = Mock()
		WeaponRequestDTO weaponRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = weaponRestQuery.query(weaponRestBeanParams)

		then:
		1 * weaponBaseRestMapperMock.mapBase(weaponRestBeanParams) >> weaponRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(weaponRestBeanParams) >> pageRequest
		1 * weaponRepositoryMock.findMatching(weaponRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps WeaponV2RestBeanParams to WeaponRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		WeaponV2RestBeanParams weaponV2RestBeanParams = Mock()
		WeaponRequestDTO weaponRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = weaponRestQuery.query(weaponV2RestBeanParams)

		then:
		1 * weaponBaseRestMapperMock.mapV2Base(weaponV2RestBeanParams) >> weaponRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(weaponV2RestBeanParams) >> pageRequest
		1 * weaponRepositoryMock.findMatching(weaponRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
