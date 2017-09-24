package com.cezarykluczynski.stapi.server.material.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBase
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFull
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper
import com.cezarykluczynski.stapi.server.material.query.MaterialRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MaterialRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private MaterialRestQuery materialRestQueryBuilderMock

	private MaterialBaseRestMapper materialBaseRestMapperMock

	private MaterialFullRestMapper materialFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MaterialRestReader materialRestReader

	void setup() {
		materialRestQueryBuilderMock = Mock()
		materialBaseRestMapperMock = Mock()
		materialFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		materialRestReader = new MaterialRestReader(materialRestQueryBuilderMock, materialBaseRestMapperMock, materialFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		MaterialBase materialBase = Mock()
		Material material = Mock()
		MaterialRestBeanParams materialRestBeanParams = Mock()
		List<MaterialBase> restMaterialList = Lists.newArrayList(materialBase)
		List<Material> materialList = Lists.newArrayList(material)
		Page<Material> materialPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		MaterialBaseResponse materialResponseOutput = materialRestReader.readBase(materialRestBeanParams)

		then:
		1 * materialRestQueryBuilderMock.query(materialRestBeanParams) >> materialPage
		1 * pageMapperMock.fromPageToRestResponsePage(materialPage) >> responsePage
		1 * materialRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * materialPage.content >> materialList
		1 * materialBaseRestMapperMock.mapBase(materialList) >> restMaterialList
		0 * _
		materialResponseOutput.materials == restMaterialList
		materialResponseOutput.page == responsePage
		materialResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		MaterialFull materialFull = Mock()
		Material material = Mock()
		List<Material> materialList = Lists.newArrayList(material)
		Page<Material> materialPage = Mock()

		when:
		MaterialFullResponse materialResponseOutput = materialRestReader.readFull(UID)

		then:
		1 * materialRestQueryBuilderMock.query(_ as MaterialRestBeanParams) >> { MaterialRestBeanParams materialRestBeanParams ->
			assert materialRestBeanParams.uid == UID
			materialPage
		}
		1 * materialPage.content >> materialList
		1 * materialFullRestMapperMock.mapFull(material) >> materialFull
		0 * _
		materialResponseOutput.material == materialFull
	}

	void "requires UID in full request"() {
		when:
		materialRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
