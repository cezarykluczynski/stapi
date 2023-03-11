package com.cezarykluczynski.stapi.server.weapon.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2Base
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2Full
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2FullResponse
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper
import com.cezarykluczynski.stapi.server.weapon.query.WeaponRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class WeaponV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private WeaponRestQuery weaponRestQueryBuilderMock

	private WeaponBaseRestMapper weaponBaseRestMapperMock

	private WeaponFullRestMapper weaponFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private WeaponV2RestReader weaponV2RestReader

	void setup() {
		weaponRestQueryBuilderMock = Mock()
		weaponBaseRestMapperMock = Mock()
		weaponFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		weaponV2RestReader = new WeaponV2RestReader(weaponRestQueryBuilderMock, weaponBaseRestMapperMock, weaponFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		WeaponV2Base weaponV2Base = Mock()
		Weapon weapon = Mock()
		WeaponV2RestBeanParams weaponV2RestBeanParams = Mock()
		List<WeaponV2Base> restWeaponList = Lists.newArrayList(weaponV2Base)
		List<Weapon> weaponList = Lists.newArrayList(weapon)
		Page<Weapon> weaponPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		WeaponV2BaseResponse weaponResponseOutput = weaponV2RestReader.readBase(weaponV2RestBeanParams)

		then:
		1 * weaponRestQueryBuilderMock.query(weaponV2RestBeanParams) >> weaponPage
		1 * pageMapperMock.fromPageToRestResponsePage(weaponPage) >> responsePage
		1 * weaponV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * weaponPage.content >> weaponList
		1 * weaponBaseRestMapperMock.mapV2Base(weaponList) >> restWeaponList
		0 * _
		weaponResponseOutput.weapons == restWeaponList
		weaponResponseOutput.page == responsePage
		weaponResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		WeaponV2Full weaponV2Full = Mock()
		Weapon weapon = Mock()
		List<Weapon> weaponList = Lists.newArrayList(weapon)
		Page<Weapon> weaponPage = Mock()

		when:
		WeaponV2FullResponse weaponResponseOutput = weaponV2RestReader.readFull(UID)

		then:
		1 * weaponRestQueryBuilderMock.query(_ as WeaponV2RestBeanParams) >> { WeaponV2RestBeanParams weaponV2RestBeanParams ->
			assert weaponV2RestBeanParams.uid == UID
			weaponPage
		}
		1 * weaponPage.content >> weaponList
		1 * weaponFullRestMapperMock.mapV2Full(weapon) >> weaponV2Full
		0 * _
		weaponResponseOutput.weapon == weaponV2Full
	}

	void "requires UID in full request"() {
		when:
		weaponV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
