package com.cezarykluczynski.stapi.server.spacecraft_class.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3Full
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3FullResponse
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SpacecraftClassV3RestReaderTest extends Specification {

	private static final String UID = 'UID'

	private SpacecraftClassRestQuery spacecraftClassRestQueryBuilderMock

	private SpacecraftClassFullRestMapper spacecraftClassFullRestMapperMock

	private SpacecraftClassV3RestReader spacecraftClassV3RestReader

	void setup() {
		spacecraftClassRestQueryBuilderMock = Mock()
		spacecraftClassFullRestMapperMock = Mock()
		spacecraftClassV3RestReader = new SpacecraftClassV3RestReader(spacecraftClassRestQueryBuilderMock, spacecraftClassFullRestMapperMock)
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SpacecraftClassV3Full spacecraftClassV3Full = Mock()
		SpacecraftClass spacecraftClass = Mock()
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(spacecraftClass)
		Page<SpacecraftClass> spacecraftClassPage = Mock()

		when:
		SpacecraftClassV3FullResponse spacecraftClassResponseOutput = spacecraftClassV3RestReader.readFull(UID)

		then:
		1 * spacecraftClassRestQueryBuilderMock.query(_ as SpacecraftClassV2RestBeanParams) >> {
			SpacecraftClassV2RestBeanParams spacecraftClassRestBeanParams ->
				assert spacecraftClassRestBeanParams.uid == UID
				spacecraftClassPage
		}
		1 * spacecraftClassPage.content >> spacecraftClassList
		1 * spacecraftClassFullRestMapperMock.mapV3Full(spacecraftClass) >> spacecraftClassV3Full
		0 * _
		spacecraftClassResponseOutput.spacecraftClass == spacecraftClassV3Full
	}

	void "requires UID in full request"() {
		when:
		spacecraftClassV3RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
