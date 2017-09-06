package com.cezarykluczynski.stapi.server.spacecraft.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFull
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private SpacecraftSoapQuery spacecraftSoapQueryBuilderMock

	private SpacecraftBaseSoapMapper spacecraftBaseSoapMapperMock

	private SpacecraftFullSoapMapper spacecraftFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftSoapReader spacecraftSoapReader

	void setup() {
		spacecraftSoapQueryBuilderMock = Mock()
		spacecraftBaseSoapMapperMock = Mock()
		spacecraftFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftSoapReader = new SpacecraftSoapReader(spacecraftSoapQueryBuilderMock, spacecraftBaseSoapMapperMock, spacecraftFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Spacecraft> spacecraftList = Lists.newArrayList()
		Page<Spacecraft> spacecraftPage = Mock()
		List<SpacecraftBase> soapSpacecraftList = Lists.newArrayList(new SpacecraftBase(uid: UID))
		SpacecraftBaseRequest spacecraftBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftBaseResponse spacecraftResponse = spacecraftSoapReader.readBase(spacecraftBaseRequest)

		then:
		1 * spacecraftSoapQueryBuilderMock.query(spacecraftBaseRequest) >> spacecraftPage
		1 * spacecraftPage.content >> spacecraftList
		1 * pageMapperMock.fromPageToSoapResponsePage(spacecraftPage) >> responsePage
		1 * spacecraftBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * spacecraftBaseSoapMapperMock.mapBase(spacecraftList) >> soapSpacecraftList
		0 * _
		spacecraftResponse.spacecrafts[0].uid == UID
		spacecraftResponse.page == responsePage
		spacecraftResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftFull spacecraftFull = new SpacecraftFull(uid: UID)
		Spacecraft spacecraft = Mock()
		Page<Spacecraft> spacecraftPage = Mock()
		SpacecraftFullRequest spacecraftFullRequest = new SpacecraftFullRequest(uid: UID)

		when:
		SpacecraftFullResponse spacecraftFullResponse = spacecraftSoapReader.readFull(spacecraftFullRequest)

		then:
		1 * spacecraftSoapQueryBuilderMock.query(spacecraftFullRequest) >> spacecraftPage
		1 * spacecraftPage.content >> Lists.newArrayList(spacecraft)
		1 * spacecraftFullSoapMapperMock.mapFull(spacecraft) >> spacecraftFull
		0 * _
		spacecraftFullResponse.spacecraft.uid == UID
	}

	void "requires UID in full request"() {
		given:
		SpacecraftFullRequest spacecraftFullRequest = Mock()

		when:
		spacecraftSoapReader.readFull(spacecraftFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
