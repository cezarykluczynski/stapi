package com.cezarykluczynski.stapi.server.medical_condition.reader

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBase
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFull
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper
import com.cezarykluczynski.stapi.server.medical_condition.query.MedicalConditionSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MedicalConditionSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private MedicalConditionSoapQuery medicalConditionSoapQueryBuilderMock

	private MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapperMock

	private MedicalConditionFullSoapMapper medicalConditionFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MedicalConditionSoapReader medicalConditionSoapReader

	void setup() {
		medicalConditionSoapQueryBuilderMock = Mock()
		medicalConditionBaseSoapMapperMock = Mock()
		medicalConditionFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		medicalConditionSoapReader = new MedicalConditionSoapReader(medicalConditionSoapQueryBuilderMock, medicalConditionBaseSoapMapperMock,
				medicalConditionFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<MedicalCondition> medicalConditionList = Lists.newArrayList()
		Page<MedicalCondition> medicalConditionPage = Mock()
		List<MedicalConditionBase> soapMedicalConditionList = Lists.newArrayList(new MedicalConditionBase(uid: UID))
		MedicalConditionBaseRequest medicalConditionBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionResponse = medicalConditionSoapReader.readBase(medicalConditionBaseRequest)

		then:
		1 * medicalConditionSoapQueryBuilderMock.query(medicalConditionBaseRequest) >> medicalConditionPage
		1 * medicalConditionPage.content >> medicalConditionList
		1 * pageMapperMock.fromPageToSoapResponsePage(medicalConditionPage) >> responsePage
		1 * medicalConditionBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * medicalConditionBaseSoapMapperMock.mapBase(medicalConditionList) >> soapMedicalConditionList
		0 * _
		medicalConditionResponse.medicalConditions[0].uid == UID
		medicalConditionResponse.page == responsePage
		medicalConditionResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		MedicalConditionFull medicalConditionFull = new MedicalConditionFull(uid: UID)
		MedicalCondition medicalCondition = Mock()
		Page<MedicalCondition> medicalConditionPage = Mock()
		MedicalConditionFullRequest medicalConditionFullRequest = new MedicalConditionFullRequest(uid: UID)

		when:
		MedicalConditionFullResponse medicalConditionFullResponse = medicalConditionSoapReader.readFull(medicalConditionFullRequest)

		then:
		1 * medicalConditionSoapQueryBuilderMock.query(medicalConditionFullRequest) >> medicalConditionPage
		1 * medicalConditionPage.content >> Lists.newArrayList(medicalCondition)
		1 * medicalConditionFullSoapMapperMock.mapFull(medicalCondition) >> medicalConditionFull
		0 * _
		medicalConditionFullResponse.medicalCondition.uid == UID
	}

	void "requires UID in full request"() {
		given:
		MedicalConditionFullRequest medicalConditionFullRequest = Mock()

		when:
		medicalConditionSoapReader.readFull(medicalConditionFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
