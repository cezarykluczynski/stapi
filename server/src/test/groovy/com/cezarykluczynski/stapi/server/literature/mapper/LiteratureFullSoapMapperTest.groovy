package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFull
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import org.mapstruct.factory.Mappers

class LiteratureFullSoapMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureFullSoapMapper literatureFullSoapMapper

	void setup() {
		literatureFullSoapMapper = Mappers.getMapper(LiteratureFullSoapMapper)
	}

	void "maps SOAP LiteratureFullRequest to LiteratureBaseRequestDTO"() {
		given:
		LiteratureFullRequest literatureFullRequest = new LiteratureFullRequest(uid: UID)

		when:
		LiteratureRequestDTO literatureRequestDTO = literatureFullSoapMapper.mapFull literatureFullRequest

		then:
		literatureRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Literature literature = createLiterature()

		when:
		LiteratureFull literatureFull = literatureFullSoapMapper.mapFull(literature)

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
