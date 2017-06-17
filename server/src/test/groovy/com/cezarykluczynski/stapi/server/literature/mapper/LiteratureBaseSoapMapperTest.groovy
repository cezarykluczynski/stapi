package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBase
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LiteratureBaseSoapMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureBaseSoapMapper literatureBaseSoapMapper

	void setup() {
		literatureBaseSoapMapper = Mappers.getMapper(LiteratureBaseSoapMapper)
	}

	void "maps SOAP LiteratureBaseRequest to LiteratureRequestDTO"() {
		given:
		LiteratureBaseRequest literatureBaseRequest = new LiteratureBaseRequest(
				title: TITLE,
				earthlyOrigin: EARTHLY_ORIGIN,
				shakespeareanWork: SHAKESPEAREAN_WORK,
				report: REPORT,
				scientificLiterature: SCIENTIFIC_LITERATURE,
				technicalManual: TECHNICAL_MANUAL,
				religiousLiterature: RELIGIOUS_LITERATURE)

		when:
		LiteratureRequestDTO literatureRequestDTO = literatureBaseSoapMapper.mapBase literatureBaseRequest

		then:
		literatureRequestDTO.title == TITLE
		literatureRequestDTO.earthlyOrigin == EARTHLY_ORIGIN
		literatureRequestDTO.shakespeareanWork == SHAKESPEAREAN_WORK
		literatureRequestDTO.report == REPORT
		literatureRequestDTO.scientificLiterature == SCIENTIFIC_LITERATURE
		literatureRequestDTO.technicalManual == TECHNICAL_MANUAL
		literatureRequestDTO.religiousLiterature == RELIGIOUS_LITERATURE
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Literature literature = createLiterature()

		when:
		LiteratureBase literatureBase = literatureBaseSoapMapper.mapBase(Lists.newArrayList(literature))[0]

		then:
		literatureBase.uid == UID
		literatureBase.title == TITLE
		literatureBase.earthlyOrigin == EARTHLY_ORIGIN
		literatureBase.shakespeareanWork == SHAKESPEAREAN_WORK
		literatureBase.report == REPORT
		literatureBase.scientificLiterature == SCIENTIFIC_LITERATURE
		literatureBase.technicalManual == TECHNICAL_MANUAL
		literatureBase.religiousLiterature == RELIGIOUS_LITERATURE
	}

}
