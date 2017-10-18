package com.cezarykluczynski.stapi.server.medical_condition.query

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MedicalConditionSoapQueryTest extends Specification {

	private MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapperMock

	private MedicalConditionFullSoapMapper medicalConditionFullSoapMapperMock

	private PageMapper pageMapperMock

	private MedicalConditionRepository medicalConditionRepositoryMock

	private MedicalConditionSoapQuery medicalConditionSoapQuery

	void setup() {
		medicalConditionBaseSoapMapperMock = Mock()
		medicalConditionFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		medicalConditionRepositoryMock = Mock()
		medicalConditionSoapQuery = new MedicalConditionSoapQuery(medicalConditionBaseSoapMapperMock, medicalConditionFullSoapMapperMock,
				pageMapperMock, medicalConditionRepositoryMock)
	}

	void "maps MedicalConditionBaseRequest to MedicalConditionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		MedicalConditionBaseRequest medicalConditionRequest = Mock()
		medicalConditionRequest.page >> requestPage
		MedicalConditionRequestDTO medicalConditionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = medicalConditionSoapQuery.query(medicalConditionRequest)

		then:
		1 * medicalConditionBaseSoapMapperMock.mapBase(medicalConditionRequest) >> medicalConditionRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * medicalConditionRepositoryMock.findMatching(medicalConditionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps MedicalConditionFullRequest to MedicalConditionRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MedicalConditionFullRequest medicalConditionRequest = Mock()
		MedicalConditionRequestDTO medicalConditionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = medicalConditionSoapQuery.query(medicalConditionRequest)

		then:
		1 * medicalConditionFullSoapMapperMock.mapFull(medicalConditionRequest) >> medicalConditionRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * medicalConditionRepositoryMock.findMatching(medicalConditionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
