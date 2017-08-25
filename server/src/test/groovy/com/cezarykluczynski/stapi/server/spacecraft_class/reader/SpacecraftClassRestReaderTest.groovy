package com.cezarykluczynski.stapi.server.spacecraft_class.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftClassRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpacecraftClassRestQuery spacecraftClassRestQueryBuilderMock

	private SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapperMock

	private SpacecraftClassFullRestMapper spacecraftClassFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftClassRestReader spacecraftClassRestReader

	void setup() {
		spacecraftClassRestQueryBuilderMock = Mock()
		spacecraftClassBaseRestMapperMock = Mock()
		spacecraftClassFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftClassRestReader = new SpacecraftClassRestReader(spacecraftClassRestQueryBuilderMock, spacecraftClassBaseRestMapperMock,
				spacecraftClassFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassBase spacecraftClassBase = Mock()
		SpacecraftClass spacecraftClass = Mock()
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = Mock()
		List<SpacecraftClassBase> restSpacecraftClassList = Lists.newArrayList(spacecraftClassBase)
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(spacecraftClass)
		Page<SpacecraftClass> spacecraftClassPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassResponseOutput = spacecraftClassRestReader.readBase(spacecraftClassRestBeanParams)

		then:
		1 * spacecraftClassRestQueryBuilderMock.query(spacecraftClassRestBeanParams) >> spacecraftClassPage
		1 * pageMapperMock.fromPageToRestResponsePage(spacecraftClassPage) >> responsePage
		1 * spacecraftClassRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * spacecraftClassBaseRestMapperMock.mapBase(spacecraftClassList) >> restSpacecraftClassList
		0 * _
		spacecraftClassResponseOutput.spacecraftClasses == restSpacecraftClassList
		spacecraftClassResponseOutput.page == responsePage
		spacecraftClassResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassFull spacecraftClassFull = Mock()
		SpacecraftClass spacecraftClass = Mock()
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(spacecraftClass)
		Page<SpacecraftClass> spacecraftClassPage = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassResponseOutput = spacecraftClassRestReader.readFull(UID)

		then:
		1 * spacecraftClassRestQueryBuilderMock.query(_ as SpacecraftClassRestBeanParams) >> {
				SpacecraftClassRestBeanParams spacecraftClassRestBeanParams ->
			assert spacecraftClassRestBeanParams.uid == UID
			spacecraftClassPage
		}
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * spacecraftClassFullRestMapperMock.mapFull(spacecraftClass) >> spacecraftClassFull
		0 * _
		spacecraftClassResponseOutput.spacecraftClass == spacecraftClassFull
	}

	void "requires UID in full request"() {
		when:
		spacecraftClassRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
