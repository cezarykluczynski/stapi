package com.cezarykluczynski.stapi.server.weapon.query

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseSoapMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class WeaponSoapQueryTest extends Specification {

	private WeaponBaseSoapMapper weaponBaseSoapMapperMock

	private WeaponFullSoapMapper weaponFullSoapMapperMock

	private PageMapper pageMapperMock

	private WeaponRepository weaponRepositoryMock

	private WeaponSoapQuery weaponSoapQuery

	void setup() {
		weaponBaseSoapMapperMock = Mock()
		weaponFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		weaponRepositoryMock = Mock()
		weaponSoapQuery = new WeaponSoapQuery(weaponBaseSoapMapperMock, weaponFullSoapMapperMock, pageMapperMock, weaponRepositoryMock)
	}

	void "maps WeaponBaseRequest to WeaponRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		WeaponBaseRequest weaponRequest = Mock()
		weaponRequest.page >> requestPage
		WeaponRequestDTO weaponRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = weaponSoapQuery.query(weaponRequest)

		then:
		1 * weaponBaseSoapMapperMock.mapBase(weaponRequest) >> weaponRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * weaponRepositoryMock.findMatching(weaponRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps WeaponFullRequest to WeaponRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		WeaponFullRequest weaponRequest = Mock()
		WeaponRequestDTO weaponRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = weaponSoapQuery.query(weaponRequest)

		then:
		1 * weaponFullSoapMapperMock.mapFull(weaponRequest) >> weaponRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * weaponRepositoryMock.findMatching(weaponRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
