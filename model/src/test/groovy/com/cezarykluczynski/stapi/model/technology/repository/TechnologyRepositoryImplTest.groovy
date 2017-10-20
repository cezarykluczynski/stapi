package com.cezarykluczynski.stapi.model.technology.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.technology.entity.Technology_
import com.cezarykluczynski.stapi.model.technology.query.TechnologyQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractTechnologyTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class TechnologyRepositoryImplTest extends AbstractTechnologyTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private TechnologyQueryBuilderFactory technologyQueryBuilderFactory

	private TechnologyRepositoryImpl technologyRepositoryImpl

	private QueryBuilder<Technology> technologyQueryBuilder

	private Pageable pageable

	private TechnologyRequestDTO technologyRequestDTO

	private Technology technology

	private Page page

	void setup() {
		technologyQueryBuilderFactory = Mock()
		technologyRepositoryImpl = new TechnologyRepositoryImpl(technologyQueryBuilderFactory)
		technologyQueryBuilder = Mock()
		pageable = Mock()
		technologyRequestDTO = Mock()
		page = Mock()
		technology = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = technologyRepositoryImpl.findMatching(technologyRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * technologyQueryBuilderFactory.createQueryBuilder(pageable) >> technologyQueryBuilder

		then: 'uid criteria is set'
		1 * technologyRequestDTO.uid >> UID
		1 * technologyQueryBuilder.equal(Technology_.uid, UID)

		then: 'string criteria are set'
		1 * technologyRequestDTO.name >> NAME
		1 * technologyQueryBuilder.like(Technology_.name, NAME)

		then: 'boolean criteria are set'
		1 * technologyRequestDTO.borgTechnology >> BORG_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.borgTechnology, BORG_TECHNOLOGY)
		1 * technologyRequestDTO.borgComponent >> BORG_COMPONENT
		1 * technologyQueryBuilder.equal(Technology_.borgComponent, BORG_COMPONENT)
		1 * technologyRequestDTO.communicationsTechnology >> COMMUNICATIONS_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.communicationsTechnology, COMMUNICATIONS_TECHNOLOGY)
		1 * technologyRequestDTO.computerTechnology >> COMPUTER_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.computerTechnology, COMPUTER_TECHNOLOGY)
		1 * technologyRequestDTO.computerProgramming >> COMPUTER_PROGRAMMING
		1 * technologyQueryBuilder.equal(Technology_.computerProgramming, COMPUTER_PROGRAMMING)
		1 * technologyRequestDTO.subroutine >> SUBROUTINE
		1 * technologyQueryBuilder.equal(Technology_.subroutine, SUBROUTINE)
		1 * technologyRequestDTO.database >> DATABASE
		1 * technologyQueryBuilder.equal(Technology_.database, DATABASE)
		1 * technologyRequestDTO.energyTechnology >> ENERGY_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.energyTechnology, ENERGY_TECHNOLOGY)
		1 * technologyRequestDTO.fictionalTechnology >> FICTIONAL_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.fictionalTechnology, FICTIONAL_TECHNOLOGY)
		1 * technologyRequestDTO.holographicTechnology >> HOLOGRAPHIC_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.holographicTechnology, HOLOGRAPHIC_TECHNOLOGY)
		1 * technologyRequestDTO.identificationTechnology >> IDENTIFICATION_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.identificationTechnology, IDENTIFICATION_TECHNOLOGY)
		1 * technologyRequestDTO.lifeSupportTechnology >> LIFE_SUPPORT_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.lifeSupportTechnology, LIFE_SUPPORT_TECHNOLOGY)
		1 * technologyRequestDTO.sensorTechnology >> SENSOR_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.sensorTechnology, SENSOR_TECHNOLOGY)
		1 * technologyRequestDTO.shieldTechnology >> SHIELD_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.shieldTechnology, SHIELD_TECHNOLOGY)
		1 * technologyRequestDTO.tool >> TOOL
		1 * technologyQueryBuilder.equal(Technology_.tool, TOOL)
		1 * technologyRequestDTO.culinaryTool >> CULINARY_TOOL
		1 * technologyQueryBuilder.equal(Technology_.culinaryTool, CULINARY_TOOL)
		1 * technologyRequestDTO.engineeringTool >> ENGINEERING_TOOL
		1 * technologyQueryBuilder.equal(Technology_.engineeringTool, ENGINEERING_TOOL)
		1 * technologyRequestDTO.householdTool >> HOUSEHOLD_TOOL
		1 * technologyQueryBuilder.equal(Technology_.householdTool, HOUSEHOLD_TOOL)
		1 * technologyRequestDTO.medicalEquipment >> MEDICAL_EQUIPMENT
		1 * technologyQueryBuilder.equal(Technology_.medicalEquipment, MEDICAL_EQUIPMENT)
		1 * technologyRequestDTO.transporterTechnology >> TRANSPORTER_TECHNOLOGY
		1 * technologyQueryBuilder.equal(Technology_.transporterTechnology, TRANSPORTER_TECHNOLOGY)

		then: 'sort is set'
		1 * technologyRequestDTO.sort >> SORT
		1 * technologyQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * technologyQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
