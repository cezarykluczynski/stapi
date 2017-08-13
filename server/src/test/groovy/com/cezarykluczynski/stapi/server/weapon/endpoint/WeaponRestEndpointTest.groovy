package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponRestReader

class WeaponRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private WeaponRestReader weaponRestReaderMock

	private WeaponRestEndpoint weaponRestEndpoint

	void setup() {
		weaponRestReaderMock = Mock()
		weaponRestEndpoint = new WeaponRestEndpoint(weaponRestReaderMock)
	}

	void "passes get call to WeaponRestReader"() {
		given:
		WeaponFullResponse weaponFullResponse = Mock()

		when:
		WeaponFullResponse weaponFullResponseOutput = weaponRestEndpoint.getWeapon(UID)

		then:
		1 * weaponRestReaderMock.readFull(UID) >> weaponFullResponse
		weaponFullResponseOutput == weaponFullResponse
	}

	void "passes search get call to WeaponRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		WeaponBaseResponse weaponResponse = Mock()

		when:
		WeaponBaseResponse weaponResponseOutput = weaponRestEndpoint.searchWeapons(pageAwareBeanParams)

		then:
		1 * weaponRestReaderMock.readBase(_ as WeaponRestBeanParams) >> { WeaponRestBeanParams weaponRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			weaponResponse
		}
		weaponResponseOutput == weaponResponse
	}

	void "passes search post call to WeaponRestReader"() {
		given:
		WeaponRestBeanParams weaponRestBeanParams = new WeaponRestBeanParams(name: NAME)
		WeaponBaseResponse weaponResponse = Mock()

		when:
		WeaponBaseResponse weaponResponseOutput = weaponRestEndpoint.searchWeapons(weaponRestBeanParams)

		then:
		1 * weaponRestReaderMock.readBase(weaponRestBeanParams as WeaponRestBeanParams) >> { WeaponRestBeanParams params ->
			assert params.name == NAME
			weaponResponse
		}
		weaponResponseOutput == weaponResponse
	}

}
