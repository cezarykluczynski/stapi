package com.cezarykluczynski.stapi.server.material.reader

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBase
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFull
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper
import com.cezarykluczynski.stapi.server.material.query.MaterialSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MaterialSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private MaterialSoapQuery materialSoapQueryBuilderMock

	private MaterialBaseSoapMapper materialBaseSoapMapperMock

	private MaterialFullSoapMapper materialFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MaterialSoapReader materialSoapReader

	void setup() {
		materialSoapQueryBuilderMock = Mock()
		materialBaseSoapMapperMock = Mock()
		materialFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		materialSoapReader = new MaterialSoapReader(materialSoapQueryBuilderMock, materialBaseSoapMapperMock, materialFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Material> materialList = Lists.newArrayList()
		Page<Material> materialPage = Mock()
		List<MaterialBase> soapMaterialList = Lists.newArrayList(new MaterialBase(uid: UID))
		MaterialBaseRequest materialBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		MaterialBaseResponse materialResponse = materialSoapReader.readBase(materialBaseRequest)

		then:
		1 * materialSoapQueryBuilderMock.query(materialBaseRequest) >> materialPage
		1 * materialPage.content >> materialList
		1 * pageMapperMock.fromPageToSoapResponsePage(materialPage) >> responsePage
		1 * materialBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * materialBaseSoapMapperMock.mapBase(materialList) >> soapMaterialList
		0 * _
		materialResponse.materials[0].uid == UID
		materialResponse.page == responsePage
		materialResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		MaterialFull materialFull = new MaterialFull(uid: UID)
		Material material = Mock()
		Page<Material> materialPage = Mock()
		MaterialFullRequest materialFullRequest = new MaterialFullRequest(uid: UID)

		when:
		MaterialFullResponse materialFullResponse = materialSoapReader.readFull(materialFullRequest)

		then:
		1 * materialSoapQueryBuilderMock.query(materialFullRequest) >> materialPage
		1 * materialPage.content >> Lists.newArrayList(material)
		1 * materialFullSoapMapperMock.mapFull(material) >> materialFull
		0 * _
		materialFullResponse.material.uid == UID
	}

	void "requires UID in full request"() {
		given:
		MaterialFullRequest materialFullRequest = Mock()

		when:
		materialSoapReader.readFull(materialFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
