package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import spock.lang.Specification
import spock.lang.Unroll

class IndividualTemplateWithCharacterboxTemplateEnrichingProcessorTest extends Specification {

	private static final Gender GENDER_F = Gender.F
	private static final Gender GENDER_M = Gender.M
	private static final Integer HEIGHT_1 = 1
	private static final Integer HEIGHT_2 = 2
	private static final Integer WEIGHT_1 = 3
	private static final Integer WEIGHT_2 = 4
	private static final MaritalStatus MARITAL_STATUS_MARRIED = MaritalStatus.MARRIED
	private static final MaritalStatus MARITAL_STATUS_DIVORCED = MaritalStatus.DIVORCED

	private IndividualTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessor

	def setup() {
		individualTemplateWithCharacterboxTemplateEnrichingProcessor = new IndividualTemplateWithCharacterboxTemplateEnrichingProcessor()
	}

	@Unroll("sets IndividualTemplate gender to #expectedIndividualGender when IndividualTemplate has #individualGender and CharacterboxTemplate has #characterboxGender")
	def "sets gender values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(gender: characterboxGender)
		IndividualTemplate individualTemplate = new IndividualTemplate(gender: individualGender)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.gender == expectedIndividualGender

		where:
		individualGender | characterboxGender | expectedIndividualGender
		null             | GENDER_F           | GENDER_F
		GENDER_F         | GENDER_F           | GENDER_F
		GENDER_M         | GENDER_F           | GENDER_M
		GENDER_F         | null               | GENDER_F
	}

	@Unroll("sets IndividualTemplate weight to #expectedIndividualWeight when IndividualTemplate has #individualWeight and CharacterboxTemplate has #characterboxWeight")
	def "sets weight values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(weight: characterboxWeight)
		IndividualTemplate individualTemplate = new IndividualTemplate(weight: individualWeight)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.weight == expectedIndividualWeight

		where:
		individualWeight | characterboxWeight | expectedIndividualWeight
		null             | WEIGHT_1           | WEIGHT_1
		WEIGHT_1         | WEIGHT_1           | WEIGHT_1
		WEIGHT_2         | WEIGHT_1           | WEIGHT_2
		WEIGHT_1         | null               | WEIGHT_1
	}

	@Unroll("sets IndividualTemplate height to #expectedIndividualHeight when IndividualTemplate has #individualHeight and CharacterboxTemplate has #characterboxHeight")
	def "sets height values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(height: characterboxHeight)
		IndividualTemplate individualTemplate = new IndividualTemplate(height: individualHeight)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.height == expectedIndividualHeight

		where:
		individualHeight | characterboxHeight | expectedIndividualHeight
		null             | HEIGHT_1           | HEIGHT_1
		HEIGHT_1         | HEIGHT_1           | HEIGHT_1
		HEIGHT_2         | HEIGHT_1           | HEIGHT_2
		HEIGHT_1         | null               | HEIGHT_1
	}


	@Unroll("sets IndividualTemplate marital status to #expectedIndividualMaritalStatus when IndividualTemplate has #individualMaritalStatus and CharacterboxTemplate has #characterboxMaritalStatus")
	def "sets marital status values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(maritalStatus: characterboxMaritalStatus)
		IndividualTemplate individualTemplate = new IndividualTemplate(maritalStatus: individualMaritalStatus)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.maritalStatus == expectedIndividualMaritalStatus

		where:
		individualMaritalStatus | characterboxMaritalStatus | expectedIndividualMaritalStatus
		null                    | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_MARRIED
		MARITAL_STATUS_MARRIED  | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_MARRIED
		MARITAL_STATUS_DIVORCED | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_DIVORCED
		MARITAL_STATUS_MARRIED  | null                      | MARITAL_STATUS_MARRIED
	}

}
