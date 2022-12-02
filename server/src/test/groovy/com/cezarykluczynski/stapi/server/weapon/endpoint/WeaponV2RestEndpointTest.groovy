package com.cezarykluczynski.stapi.server.weapon.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponV2RestReader

class WeaponV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private WeaponV2RestReader weaponV2RestReaderMock

	private WeaponV2RestEndpoint weaponV2RestEndpoint

	void setup() {
		weaponV2RestReaderMock = Mock()
		weaponV2RestEndpoint = new WeaponV2RestEndpoint(weaponV2RestReaderMock)
	}

	void "passes get call to WeaponV2RestReader"() {
		given:
		WeaponV2FullResponse weaponV2FullResponse = Mock()

		when:
		WeaponV2FullResponse weaponV2FullResponseOutput = weaponV2RestEndpoint.getWeapon(UID)

		then:
		1 * weaponV2RestReaderMock.readFull(UID) >> weaponV2FullResponse
		weaponV2FullResponseOutput == weaponV2FullResponse
	}

	void "passes search get call to WeaponV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		WeaponV2BaseResponse weaponV2Response = Mock()

		when:
		WeaponV2BaseResponse weaponV2ResponseOutput = weaponV2RestEndpoint.searchWeapons(pageAwareBeanParams)

		then:
		1 * weaponV2RestReaderMock.readBase(_ as WeaponV2RestBeanParams) >> { WeaponV2RestBeanParams weaponV2RestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			weaponV2Response
		}
		weaponV2ResponseOutput == weaponV2Response
	}

	void "passes search post call to WeaponV2RestReader"() {
		given:
		WeaponV2RestBeanParams weaponV2RestBeanParams = new WeaponV2RestBeanParams(name: NAME)
		WeaponV2BaseResponse weaponV2Response = Mock()

		when:
		WeaponV2BaseResponse weaponV2ResponseOutput = weaponV2RestEndpoint.searchWeapons(weaponV2RestBeanParams)

		then:
		1 * weaponV2RestReaderMock.readBase(weaponV2RestBeanParams as WeaponV2RestBeanParams) >> { WeaponV2RestBeanParams params ->
			assert params.name == NAME
			weaponV2Response
		}
		weaponV2ResponseOutput == weaponV2Response
	}

}
