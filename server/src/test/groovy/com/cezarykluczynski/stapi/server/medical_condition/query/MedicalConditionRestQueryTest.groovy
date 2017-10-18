package com.cezarykluczynski.stapi.server.medical_condition.query

import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MedicalConditionRestQueryTest extends Specification {

	private MedicalConditionBaseRestMapper medicalConditionRestMapperMock

	private PageMapper pageMapperMock

	private MedicalConditionRepository medicalConditionRepositoryMock

	private MedicalConditionRestQuery medicalConditionRestQuery

	void setup() {
		medicalConditionRestMapperMock = Mock()
		pageMapperMock = Mock()
		medicalConditionRepositoryMock = Mock()
		medicalConditionRestQuery = new MedicalConditionRestQuery(medicalConditionRestMapperMock, pageMapperMock, medicalConditionRepositoryMock)
	}

	void "maps MedicalConditionRestBeanParams to MedicalConditionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = Mock()
		MedicalConditionRequestDTO medicalConditionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = medicalConditionRestQuery.query(medicalConditionRestBeanParams)

		then:
		1 * medicalConditionRestMapperMock.mapBase(medicalConditionRestBeanParams) >> medicalConditionRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(medicalConditionRestBeanParams) >> pageRequest
		1 * medicalConditionRepositoryMock.findMatching(medicalConditionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
