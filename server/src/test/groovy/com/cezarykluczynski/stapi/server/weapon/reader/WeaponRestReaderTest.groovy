package com.cezarykluczynski.stapi.server.weapon.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBase
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFull
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper
import com.cezarykluczynski.stapi.server.weapon.query.WeaponRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class WeaponRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private WeaponRestQuery weaponRestQueryBuilderMock

	private WeaponBaseRestMapper weaponBaseRestMapperMock

	private WeaponFullRestMapper weaponFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private WeaponRestReader weaponRestReader

	void setup() {
		weaponRestQueryBuilderMock = Mock()
		weaponBaseRestMapperMock = Mock()
		weaponFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		weaponRestReader = new WeaponRestReader(weaponRestQueryBuilderMock, weaponBaseRestMapperMock, weaponFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		WeaponBase weaponBase = Mock()
		Weapon weapon = Mock()
		WeaponRestBeanParams weaponRestBeanParams = Mock()
		List<WeaponBase> restWeaponList = Lists.newArrayList(weaponBase)
		List<Weapon> weaponList = Lists.newArrayList(weapon)
		Page<Weapon> weaponPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		WeaponBaseResponse weaponResponseOutput = weaponRestReader.readBase(weaponRestBeanParams)

		then:
		1 * weaponRestQueryBuilderMock.query(weaponRestBeanParams) >> weaponPage
		1 * pageMapperMock.fromPageToRestResponsePage(weaponPage) >> responsePage
		1 * weaponRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * weaponPage.content >> weaponList
		1 * weaponBaseRestMapperMock.mapBase(weaponList) >> restWeaponList
		0 * _
		weaponResponseOutput.weapons == restWeaponList
		weaponResponseOutput.page == responsePage
		weaponResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		WeaponFull weaponFull = Mock()
		Weapon weapon = Mock()
		List<Weapon> weaponList = Lists.newArrayList(weapon)
		Page<Weapon> weaponPage = Mock()

		when:
		WeaponFullResponse weaponResponseOutput = weaponRestReader.readFull(UID)

		then:
		1 * weaponRestQueryBuilderMock.query(_ as WeaponRestBeanParams) >> { WeaponRestBeanParams weaponRestBeanParams ->
			assert weaponRestBeanParams.uid == UID
			weaponPage
		}
		1 * weaponPage.content >> weaponList
		1 * weaponFullRestMapperMock.mapFull(weapon) >> weaponFull
		0 * _
		weaponResponseOutput.weapon == weaponFull
	}

	void "requires UID in full request"() {
		when:
		weaponRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
