package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBase
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LiteratureBaseRestMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureBaseRestMapper literatureBaseRestMapper

	void setup() {
		literatureBaseRestMapper = Mappers.getMapper(LiteratureBaseRestMapper)
	}

	void "maps LiteratureRestBeanParams to LiteratureRequestDTO"() {
		given:
		LiteratureRestBeanParams literatureRestBeanParams = new LiteratureRestBeanParams(
				title: TITLE,
				earthlyOrigin: EARTHLY_ORIGIN,
				shakespeareanWork: SHAKESPEAREAN_WORK,
				report: REPORT,
				scientificLiterature: SCIENTIFIC_LITERATURE,
				technicalManual: TECHNICAL_MANUAL,
				religiousLiterature: RELIGIOUS_LITERATURE)

		when:
		LiteratureRequestDTO literatureRequestDTO = literatureBaseRestMapper.mapBase literatureRestBeanParams

		then:
		literatureRequestDTO.title == TITLE
		literatureRequestDTO.earthlyOrigin == EARTHLY_ORIGIN
		literatureRequestDTO.shakespeareanWork == SHAKESPEAREAN_WORK
		literatureRequestDTO.report == REPORT
		literatureRequestDTO.scientificLiterature == SCIENTIFIC_LITERATURE
		literatureRequestDTO.technicalManual == TECHNICAL_MANUAL
		literatureRequestDTO.religiousLiterature == RELIGIOUS_LITERATURE
	}

	void "maps DB entity to base REST entity"() {
		given:
		Literature literature = createLiterature()

		when:
		LiteratureBase literatureBase = literatureBaseRestMapper.mapBase(Lists.newArrayList(literature))[0]

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
