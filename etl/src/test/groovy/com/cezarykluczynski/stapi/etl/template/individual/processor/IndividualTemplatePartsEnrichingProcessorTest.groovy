package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String VALUE = 'VALUE'
	private static final Integer HEIGHT = 183
	private static final Integer WEIGHT = 77
	private static final Integer YEAR = 1970
	private static final Integer MONTH = 10
	private static final Integer DAY = 7
	private static final String PLACE = 'PLACE'
	private static final Gender GENDER = Gender.F
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessorMock

	private IndividualActorLinkingProcessor individualActorLinkingProcessorMock

	private IndividualHeightProcessor individualHeightProcessorMock

	private IndividualWeightProcessor individualWeightProcessorMock

	private IndividualBloodTypeProcessor individualBloodTypeProcessorMock

	private MaritalStatusProcessor maritalStatusProcessorMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor

	def setup() {
		partToGenderProcessorMock = Mock(PartToGenderProcessor)
		individualLifeBoundaryProcessorMock = Mock(IndividualLifeBoundaryProcessor)
		individualActorLinkingProcessorMock = Mock(IndividualActorLinkingProcessor)
		individualHeightProcessorMock = Mock(IndividualHeightProcessor)
		individualWeightProcessorMock = Mock(IndividualWeightProcessor)
		individualBloodTypeProcessorMock = Mock(IndividualBloodTypeProcessor)
		maritalStatusProcessorMock = Mock(MaritalStatusProcessor)
		individualTemplatePartsEnrichingProcessor = new IndividualTemplatePartsEnrichingProcessor(partToGenderProcessorMock,
				individualLifeBoundaryProcessorMock, individualActorLinkingProcessorMock, individualHeightProcessorMock,
				individualWeightProcessorMock, individualBloodTypeProcessorMock, maritalStatusProcessorMock)
	}

	def "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplatePartsEnrichingProcessor.GENDER)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		individualTemplate.gender == GENDER
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

	def "when actor key is found, part is passed to IndividualActorLinkingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplatePartsEnrichingProcessor.ACTOR)
		IndividualTemplate individualTemplateInActorLinkingProcessor = null
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualActorLinkingProcessorMock.enrich(_ as EnrichablePair<Template.Part, IndividualTemplate>) >> {
			EnrichablePair<Template.Part, IndividualTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				individualTemplateInActorLinkingProcessor = enrichablePair.output
		}
		0 * _
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 2
		individualTemplateInActorLinkingProcessor == individualTemplate
	}

	def "sets height from IndividualHeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.HEIGHT,
				value: VALUE)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualHeightProcessorMock.process(VALUE) >> HEIGHT
		0 * _
		individualTemplate.height == HEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

	def "sets weight from IndividualWeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.WEIGHT,
				value: VALUE)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualWeightProcessorMock.process(VALUE) >> WEIGHT
		0 * _
		individualTemplate.weight == WEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

	def "does not set serial number when it is not empty"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.SERIAL_NUMBER,
				value: "")
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		0 * _
		individualTemplate.serialNumber == null
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 2
	}

	def "sets serial number when it is not empty"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.SERIAL_NUMBER,
				value: VALUE)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		0 * _
		individualTemplate.serialNumber == VALUE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

	def "sets birth values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.BORN,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY,
				place: PLACE
		)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		individualTemplate.yearOfBirth == YEAR
		individualTemplate.monthOfBirth == MONTH
		individualTemplate.dayOfBirth == DAY
		individualTemplate.placeOfBirth == PLACE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 6
	}

	def "sets death values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.DIED,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY,
				place: PLACE
		)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		individualTemplate.yearOfDeath == YEAR
		individualTemplate.monthOfDeath == MONTH
		individualTemplate.dayOfDeath == DAY
		individualTemplate.placeOfDeath == PLACE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 6
	}

	def "sets marital status from MaritalStatusProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.MARITAL_STATUS,
				value: VALUE)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * maritalStatusProcessorMock.process(VALUE) >> MARITAL_STATUS
		0 * _
		individualTemplate.maritalStatus == MARITAL_STATUS
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

	def "sets blood type from BloodTypeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplatePartsEnrichingProcessor.BLOOD_TYPE,
				value: VALUE)
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), individualTemplate))

		then:
		1 * individualBloodTypeProcessorMock.process(VALUE) >> BLOOD_TYPE
		0 * _
		individualTemplate.bloodType == BLOOD_TYPE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 3
	}

}
