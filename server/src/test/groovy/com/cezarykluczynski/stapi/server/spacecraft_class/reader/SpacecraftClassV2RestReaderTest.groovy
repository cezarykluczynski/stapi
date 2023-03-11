package com.cezarykluczynski.stapi.server.spacecraft_class.reader

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2Base
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2Full
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftClassV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpacecraftClassRestQuery spacecraftClassRestQueryBuilderMock

	private SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapperMock

	private SpacecraftClassFullRestMapper spacecraftClassFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftClassV2RestReader spacecraftClassV2RestReader

	void setup() {
		spacecraftClassRestQueryBuilderMock = Mock()
		spacecraftClassBaseRestMapperMock = Mock()
		spacecraftClassFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftClassV2RestReader = new SpacecraftClassV2RestReader(spacecraftClassRestQueryBuilderMock, spacecraftClassBaseRestMapperMock,
				spacecraftClassFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassV2Base spacecraftClassV2Base = Mock()
		SpacecraftClass spacecraftClass = Mock()
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = Mock()
		List<SpacecraftClassV2Base> restSpacecraftClassList = Lists.newArrayList(spacecraftClassV2Base)
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(spacecraftClass)
		Page<SpacecraftClass> spacecraftClassPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2ResponseOutput = spacecraftClassV2RestReader.readBase(spacecraftClassV2RestBeanParams)

		then:
		1 * spacecraftClassRestQueryBuilderMock.query(spacecraftClassV2RestBeanParams) >> spacecraftClassPage
		1 * pageMapperMock.fromPageToRestResponsePage(spacecraftClassPage) >> responsePage
		1 * spacecraftClassV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * spacecraftClassBaseRestMapperMock.mapV2Base(spacecraftClassList) >> restSpacecraftClassList
		0 * _
		spacecraftClassV2ResponseOutput.spacecraftClasses == restSpacecraftClassList
		spacecraftClassV2ResponseOutput.page == responsePage
		spacecraftClassV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassV2Full spacecraftClassV2Full = Mock()
		SpacecraftClass spacecraftClass = Mock()
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(spacecraftClass)
		Page<SpacecraftClass> spacecraftClassPage = Mock()

		when:
		SpacecraftClassV2FullResponse spacecraftClassResponseOutput = spacecraftClassV2RestReader.readFull(UID)

		then:
		1 * spacecraftClassRestQueryBuilderMock.query(_ as SpacecraftClassV2RestBeanParams) >> {
			SpacecraftClassV2RestBeanParams spacecraftClassRestBeanParams ->
			assert spacecraftClassRestBeanParams.uid == UID
			spacecraftClassPage
		}
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * spacecraftClassFullRestMapperMock.mapV2Full(spacecraftClass) >> spacecraftClassV2Full
		0 * _
		spacecraftClassResponseOutput.spacecraftClass == spacecraftClassV2Full
	}

	void "requires UID in full request"() {
		when:
		spacecraftClassV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
