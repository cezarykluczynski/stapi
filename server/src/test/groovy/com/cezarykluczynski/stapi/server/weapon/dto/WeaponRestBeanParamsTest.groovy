package com.cezarykluczynski.stapi.server.weapon.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class WeaponRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates WeaponRestBeanParams from PageSortBeanParams"() {
		when:
		WeaponRestBeanParams weaponRestBeanParams = WeaponRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		weaponRestBeanParams.pageNumber == PAGE_NUMBER
		weaponRestBeanParams.pageSize == PAGE_SIZE
		weaponRestBeanParams.sort == SORT
	}

	void "creates null WeaponRestBeanParams from null PageSortBeanParams"() {
		when:
		WeaponRestBeanParams seriesRestBeanParams = WeaponRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
