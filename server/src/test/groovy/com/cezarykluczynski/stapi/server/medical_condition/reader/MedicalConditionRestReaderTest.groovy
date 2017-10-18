package com.cezarykluczynski.stapi.server.medical_condition.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBase
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFull
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper
import com.cezarykluczynski.stapi.server.medical_condition.query.MedicalConditionRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MedicalConditionRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private MedicalConditionRestQuery medicalConditionRestQueryBuilderMock

	private MedicalConditionBaseRestMapper medicalConditionBaseRestMapperMock

	private MedicalConditionFullRestMapper medicalConditionFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MedicalConditionRestReader medicalConditionRestReader

	void setup() {
		medicalConditionRestQueryBuilderMock = Mock()
		medicalConditionBaseRestMapperMock = Mock()
		medicalConditionFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		medicalConditionRestReader = new MedicalConditionRestReader(medicalConditionRestQueryBuilderMock, medicalConditionBaseRestMapperMock,
				medicalConditionFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		MedicalConditionBase medicalConditionBase = Mock()
		MedicalCondition medicalCondition = Mock()
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = Mock()
		List<MedicalConditionBase> restMedicalConditionList = Lists.newArrayList(medicalConditionBase)
		List<MedicalCondition> medicalConditionList = Lists.newArrayList(medicalCondition)
		Page<MedicalCondition> medicalConditionPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionResponseOutput = medicalConditionRestReader.readBase(medicalConditionRestBeanParams)

		then:
		1 * medicalConditionRestQueryBuilderMock.query(medicalConditionRestBeanParams) >> medicalConditionPage
		1 * pageMapperMock.fromPageToRestResponsePage(medicalConditionPage) >> responsePage
		1 * medicalConditionRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * medicalConditionPage.content >> medicalConditionList
		1 * medicalConditionBaseRestMapperMock.mapBase(medicalConditionList) >> restMedicalConditionList
		0 * _
		medicalConditionResponseOutput.medicalConditions == restMedicalConditionList
		medicalConditionResponseOutput.page == responsePage
		medicalConditionResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		MedicalConditionFull medicalConditionFull = Mock()
		MedicalCondition medicalCondition = Mock()
		List<MedicalCondition> medicalConditionList = Lists.newArrayList(medicalCondition)
		Page<MedicalCondition> medicalConditionPage = Mock()

		when:
		MedicalConditionFullResponse medicalConditionResponseOutput = medicalConditionRestReader.readFull(UID)

		then:
		1 * medicalConditionRestQueryBuilderMock.query(_ as MedicalConditionRestBeanParams) >> {
				MedicalConditionRestBeanParams medicalConditionRestBeanParams ->
			assert medicalConditionRestBeanParams.uid == UID
			medicalConditionPage
		}
		1 * medicalConditionPage.content >> medicalConditionList
		1 * medicalConditionFullRestMapperMock.mapFull(medicalCondition) >> medicalConditionFull
		0 * _
		medicalConditionResponseOutput.medicalCondition == medicalConditionFull
	}

	void "requires UID in full request"() {
		when:
		medicalConditionRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
