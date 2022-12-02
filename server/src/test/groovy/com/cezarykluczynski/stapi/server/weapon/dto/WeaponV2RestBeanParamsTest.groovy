package com.cezarykluczynski.stapi.server.weapon.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class WeaponV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates WeaponV2RestBeanParams from PageSortBeanParams"() {
		when:
		WeaponV2RestBeanParams weaponV2RestBeanParams = WeaponV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		weaponV2RestBeanParams.pageNumber == PAGE_NUMBER
		weaponV2RestBeanParams.pageSize == PAGE_SIZE
		weaponV2RestBeanParams.sort == SORT
	}

	void "creates null WeaponV2RestBeanParams from null PageSortBeanParams"() {
		when:
		WeaponV2RestBeanParams weaponV2RestBeanParams = WeaponV2RestBeanParams.fromPageSortBeanParams null

		then:
		weaponV2RestBeanParams == null
	}

}
