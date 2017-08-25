package com.cezarykluczynski.stapi.server.spacecraft_class.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFull
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftClassSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private SpacecraftClassSoapQuery spacecraftClassSoapQueryBuilderMock

	private SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapperMock

	private SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftClassSoapReader spacecraftClassSoapReader

	void setup() {
		spacecraftClassSoapQueryBuilderMock = Mock()
		spacecraftClassBaseSoapMapperMock = Mock()
		spacecraftClassFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftClassSoapReader = new SpacecraftClassSoapReader(spacecraftClassSoapQueryBuilderMock, spacecraftClassBaseSoapMapperMock,
				spacecraftClassFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList()
		Page<SpacecraftClass> spacecraftClassPage = Mock()
		List<SpacecraftClassBase> soapSpacecraftClassList = Lists.newArrayList(new SpacecraftClassBase(uid: UID))
		SpacecraftClassBaseRequest spacecraftClassBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassResponse = spacecraftClassSoapReader.readBase(spacecraftClassBaseRequest)

		then:
		1 * spacecraftClassSoapQueryBuilderMock.query(spacecraftClassBaseRequest) >> spacecraftClassPage
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * pageMapperMock.fromPageToSoapResponsePage(spacecraftClassPage) >> responsePage
		1 * spacecraftClassBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * spacecraftClassBaseSoapMapperMock.mapBase(spacecraftClassList) >> soapSpacecraftClassList
		0 * _
		spacecraftClassResponse.spacecraftClasss[0].uid == UID
		spacecraftClassResponse.page == responsePage
		spacecraftClassResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassFull spacecraftClassFull = new SpacecraftClassFull(uid: UID)
		SpacecraftClass spacecraftClass = Mock()
		Page<SpacecraftClass> spacecraftClassPage = Mock()
		SpacecraftClassFullRequest spacecraftClassFullRequest = new SpacecraftClassFullRequest(uid: UID)

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponse = spacecraftClassSoapReader.readFull(spacecraftClassFullRequest)

		then:
		1 * spacecraftClassSoapQueryBuilderMock.query(spacecraftClassFullRequest) >> spacecraftClassPage
		1 * spacecraftClassPage.content >> Lists.newArrayList(spacecraftClass)
		1 * spacecraftClassFullSoapMapperMock.mapFull(spacecraftClass) >> spacecraftClassFull
		0 * _
		spacecraftClassFullResponse.spacecraftClass.uid == UID
	}

	void "requires UID in full request"() {
		given:
		SpacecraftClassFullRequest spacecraftClassFullRequest = Mock()

		when:
		spacecraftClassSoapReader.readFull(spacecraftClassFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
