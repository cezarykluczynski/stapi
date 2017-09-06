package com.cezarykluczynski.stapi.server.spacecraft.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SpacecraftRestQuery spacecraftRestQueryBuilderMock

	private SpacecraftBaseRestMapper spacecraftBaseRestMapperMock

	private SpacecraftFullRestMapper spacecraftFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SpacecraftRestReader spacecraftRestReader

	void setup() {
		spacecraftRestQueryBuilderMock = Mock()
		spacecraftBaseRestMapperMock = Mock()
		spacecraftFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		spacecraftRestReader = new SpacecraftRestReader(spacecraftRestQueryBuilderMock, spacecraftBaseRestMapperMock, spacecraftFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftBase spacecraftBase = Mock()
		Spacecraft spacecraft = Mock()
		SpacecraftRestBeanParams spacecraftRestBeanParams = Mock()
		List<SpacecraftBase> restSpacecraftList = Lists.newArrayList(spacecraftBase)
		List<Spacecraft> spacecraftList = Lists.newArrayList(spacecraft)
		Page<Spacecraft> spacecraftPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SpacecraftBaseResponse spacecraftResponseOutput = spacecraftRestReader.readBase(spacecraftRestBeanParams)

		then:
		1 * spacecraftRestQueryBuilderMock.query(spacecraftRestBeanParams) >> spacecraftPage
		1 * pageMapperMock.fromPageToRestResponsePage(spacecraftPage) >> responsePage
		1 * spacecraftRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * spacecraftPage.content >> spacecraftList
		1 * spacecraftBaseRestMapperMock.mapBase(spacecraftList) >> restSpacecraftList
		0 * _
		spacecraftResponseOutput.spacecrafts == restSpacecraftList
		spacecraftResponseOutput.page == responsePage
		spacecraftResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftFull spacecraftFull = Mock()
		Spacecraft spacecraft = Mock()
		List<Spacecraft> spacecraftList = Lists.newArrayList(spacecraft)
		Page<Spacecraft> spacecraftPage = Mock()

		when:
		SpacecraftFullResponse spacecraftResponseOutput = spacecraftRestReader.readFull(UID)

		then:
		1 * spacecraftRestQueryBuilderMock.query(_ as SpacecraftRestBeanParams) >> { SpacecraftRestBeanParams spacecraftRestBeanParams ->
			assert spacecraftRestBeanParams.uid == UID
			spacecraftPage
		}
		1 * spacecraftPage.content >> spacecraftList
		1 * spacecraftFullRestMapperMock.mapFull(spacecraft) >> spacecraftFull
		0 * _
		spacecraftResponseOutput.spacecraft == spacecraftFull
	}

	void "requires UID in full request"() {
		when:
		spacecraftRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
