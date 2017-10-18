package com.cezarykluczynski.stapi.model.medical_condition.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition_
import com.cezarykluczynski.stapi.model.medical_condition.query.MedicalConditionQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractMedicalConditionTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MedicalConditionRepositoryImplTest extends AbstractMedicalConditionTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MedicalConditionQueryBuilderFactory medicalConditionQueryBuilderFactory

	private MedicalConditionRepositoryImpl medicalConditionRepositoryImpl

	private QueryBuilder<MedicalCondition> medicalConditionQueryBuilder

	private Pageable pageable

	private MedicalConditionRequestDTO medicalConditionRequestDTO

	private MedicalCondition medicalCondition

	private Page page

	void setup() {
		medicalConditionQueryBuilderFactory = Mock()
		medicalConditionRepositoryImpl = new MedicalConditionRepositoryImpl(medicalConditionQueryBuilderFactory)
		medicalConditionQueryBuilder = Mock()
		pageable = Mock()
		medicalConditionRequestDTO = Mock()
		page = Mock()
		medicalCondition = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = medicalConditionRepositoryImpl.findMatching(medicalConditionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * medicalConditionQueryBuilderFactory.createQueryBuilder(pageable) >> medicalConditionQueryBuilder

		then: 'uid criteria is set'
		1 * medicalConditionRequestDTO.uid >> UID
		1 * medicalConditionQueryBuilder.equal(MedicalCondition_.uid, UID)

		then: 'string criteria are set'
		1 * medicalConditionRequestDTO.name >> NAME
		1 * medicalConditionQueryBuilder.like(MedicalCondition_.name, NAME)

		then: 'boolean criteria are set'
		1 * medicalConditionRequestDTO.psychologicalCondition >> PSYCHOLOGICAL_CONDITION
		1 * medicalConditionQueryBuilder.equal(MedicalCondition_.psychologicalCondition, PSYCHOLOGICAL_CONDITION)

		then: 'sort is set'
		1 * medicalConditionRequestDTO.sort >> SORT
		1 * medicalConditionQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * medicalConditionQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
