package com.cezarykluczynski.stapi.server.weapon.reader

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBase
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFull
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseSoapMapper
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullSoapMapper
import com.cezarykluczynski.stapi.server.weapon.query.WeaponSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class WeaponSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private WeaponSoapQuery weaponSoapQueryBuilderMock

	private WeaponBaseSoapMapper weaponBaseSoapMapperMock

	private WeaponFullSoapMapper weaponFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private WeaponSoapReader weaponSoapReader

	void setup() {
		weaponSoapQueryBuilderMock = Mock()
		weaponBaseSoapMapperMock = Mock()
		weaponFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		weaponSoapReader = new WeaponSoapReader(weaponSoapQueryBuilderMock, weaponBaseSoapMapperMock, weaponFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Weapon> weaponList = Lists.newArrayList()
		Page<Weapon> weaponPage = Mock()
		List<WeaponBase> soapWeaponList = Lists.newArrayList(new WeaponBase(uid: UID))
		WeaponBaseRequest weaponBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		WeaponBaseResponse weaponResponse = weaponSoapReader.readBase(weaponBaseRequest)

		then:
		1 * weaponSoapQueryBuilderMock.query(weaponBaseRequest) >> weaponPage
		1 * weaponPage.content >> weaponList
		1 * pageMapperMock.fromPageToSoapResponsePage(weaponPage) >> responsePage
		1 * weaponBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * weaponBaseSoapMapperMock.mapBase(weaponList) >> soapWeaponList
		0 * _
		weaponResponse.weapons[0].uid == UID
		weaponResponse.page == responsePage
		weaponResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		WeaponFull weaponFull = new WeaponFull(uid: UID)
		Weapon weapon = Mock()
		Page<Weapon> weaponPage = Mock()
		WeaponFullRequest weaponFullRequest = new WeaponFullRequest(uid: UID)

		when:
		WeaponFullResponse weaponFullResponse = weaponSoapReader.readFull(weaponFullRequest)

		then:
		1 * weaponSoapQueryBuilderMock.query(weaponFullRequest) >> weaponPage
		1 * weaponPage.content >> Lists.newArrayList(weapon)
		1 * weaponFullSoapMapperMock.mapFull(weapon) >> weaponFull
		0 * _
		weaponFullResponse.weapon.uid == UID
	}

	void "requires UID in full request"() {
		given:
		WeaponFullRequest weaponFullRequest = Mock()

		when:
		weaponSoapReader.readFull(weaponFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
