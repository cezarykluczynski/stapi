package com.cezarykluczynski.stapi.server.spacecraft.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2Base
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2Full
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpacecraftRestQuery spacecraftRestQueryBuilderMock

	private SpacecraftBaseRestMapper spacecraftBaseRestMapperMock

	private SpacecraftFullRestMapper spacecraftFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftV2RestReader spacecraftV2RestReader

	void setup() {
		spacecraftRestQueryBuilderMock = Mock()
		spacecraftBaseRestMapperMock = Mock()
		spacecraftFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftV2RestReader = new SpacecraftV2RestReader(spacecraftRestQueryBuilderMock, spacecraftBaseRestMapperMock,
				spacecraftFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftV2Base spacecraftV2Base = Mock()
		Spacecraft spacecraft = Mock()
		SpacecraftV2RestBeanParams spacecraftRestBeanParams = Mock()
		List<SpacecraftV2Base> restSpacecraftList = Lists.newArrayList(spacecraftV2Base)
		List<Spacecraft> spacecraftList = Lists.newArrayList(spacecraft)
		Page<Spacecraft> spacecraftPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftV2BaseResponse spacecraftV2ResponseOutput = spacecraftV2RestReader.readBase(spacecraftRestBeanParams)

		then:
		1 * spacecraftRestQueryBuilderMock.query(spacecraftRestBeanParams) >> spacecraftPage
		1 * pageMapperMock.fromPageToRestResponsePage(spacecraftPage) >> responsePage
		1 * spacecraftRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * spacecraftPage.content >> spacecraftList
		1 * spacecraftBaseRestMapperMock.mapV2Base(spacecraftList) >> restSpacecraftList
		0 * _
		spacecraftV2ResponseOutput.spacecrafts == restSpacecraftList
		spacecraftV2ResponseOutput.page == responsePage
		spacecraftV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftV2Full spacecraftV2Full = Mock()
		Spacecraft spacecraft = Mock()
		List<Spacecraft> spacecraftList = Lists.newArrayList(spacecraft)
		Page<Spacecraft> spacecraftPage = Mock()

		when:
		SpacecraftV2FullResponse spacecraftResponseOutput = spacecraftV2RestReader.readFull(UID)

		then:
		1 * spacecraftRestQueryBuilderMock.query(_ as SpacecraftV2RestBeanParams) >> {
			SpacecraftV2RestBeanParams spacecraftRestBeanParams ->
				assert spacecraftRestBeanParams.uid == UID
				spacecraftPage
		}
		1 * spacecraftPage.content >> spacecraftList
		1 * spacecraftFullRestMapperMock.mapV2Full(spacecraft) >> spacecraftV2Full
		0 * _
		spacecraftResponseOutput.spacecraft == spacecraftV2Full
	}

	void "requires UID in full request"() {
		when:
		spacecraftV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
