package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFull
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import org.mapstruct.factory.Mappers

class LiteratureFullRestMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureFullRestMapper literatureFullRestMapper

	void setup() {
		literatureFullRestMapper = Mappers.getMapper(LiteratureFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Literature dBLiterature = createLiterature()

		when:
		LiteratureFull literatureFull = literatureFullRestMapper.mapFull(dBLiterature)

		then:
		literatureFull.uid == UID
		literatureFull.title == TITLE
		literatureFull.earthlyOrigin == EARTHLY_ORIGIN
		literatureFull.shakespeareanWork == SHAKESPEAREAN_WORK
		literatureFull.report == REPORT
		literatureFull.scientificLiterature == SCIENTIFIC_LITERATURE
		literatureFull.technicalManual == TECHNICAL_MANUAL
		literatureFull.religiousLiterature == RELIGIOUS_LITERATURE
	}

}
