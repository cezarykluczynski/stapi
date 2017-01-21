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
	private static final Integer YEAR_1 = 1970
	private static final Integer YEAR_2 = 1975
	private static final Integer MONTH_1 = 7
	private static final Integer MONTH_2 = 11
	private static final Integer DAY_1 = 7
	private static final Integer DAY_2 = 16
	private static final String PLACE_1 = 'PLACE_1'
	private static final String PLACE_2 = 'PLACE_2'
	private static final MaritalStatus MARITAL_STATUS_MARRIED = MaritalStatus.MARRIED
	private static final MaritalStatus MARITAL_STATUS_DIVORCED = MaritalStatus.DIVORCED

	private IndividualTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessor

	void setup() {
		individualTemplateWithCharacterboxTemplateEnrichingProcessor = new IndividualTemplateWithCharacterboxTemplateEnrichingProcessor()
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate gender to #expectedIndividualGender when IndividualTemplate has #individualGender and CharacterboxTemplate has #characterboxGender')
	void "sets gender values of IndividualTemplate with values of CharacterboxTemplate"() {
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

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate weight to #expectedIndividualWeight when IndividualTemplate has #individualWeight and CharacterboxTemplate has #characterboxWeight')
	void "sets weight values of IndividualTemplate with values of CharacterboxTemplate"() {
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

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate height to #expectedIndividualHeight when IndividualTemplate has #individualHeight and CharacterboxTemplate has #characterboxHeight')
	void "sets height values of IndividualTemplate with values of CharacterboxTemplate"() {
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

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate marital status to #expectedIndividualMaritalStatus when IndividualTemplate has #individualMaritalStatus and CharacterboxTemplate has #characterboxMaritalStatus')
	void "sets marital status values of IndividualTemplate with values of CharacterboxTemplate"() {
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

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate year of birth to #expectedIndividualYearOfBirth when IndividualTemplate has #individualYearOfBirth and CharacterboxTemplate has #characterboxYearOfBirth')
	void "sets year of birth values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(yearOfBirth: characterboxYearOfBirth)
		IndividualTemplate individualTemplate = new IndividualTemplate(yearOfBirth: individualYearOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.yearOfBirth == expectedIndividualYearOfBirth

		where:
		individualYearOfBirth | characterboxYearOfBirth | expectedIndividualYearOfBirth
		null                  | YEAR_1                  | YEAR_1
		YEAR_1                | YEAR_1                  | YEAR_1
		YEAR_2                | YEAR_1                  | YEAR_2
		YEAR_1                | null                    | YEAR_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate month of birth to #expectedIndividualMonthOfBirth when IndividualTemplate has #individualMonthOfBirth and CharacterboxTemplate has #characterboxMonthOfBirth')
	void "sets month of birth values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(monthOfBirth: characterboxMonthOfBirth)
		IndividualTemplate individualTemplate = new IndividualTemplate(monthOfBirth: individualMonthOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.monthOfBirth == expectedIndividualMonthOfBirth

		where:
		individualMonthOfBirth | characterboxMonthOfBirth | expectedIndividualMonthOfBirth
		null                   | MONTH_1                  | MONTH_1
		MONTH_1                | MONTH_1                  | MONTH_1
		MONTH_2                | MONTH_1                  | MONTH_2
		MONTH_1                | null                     | MONTH_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate day of birth to #expectedIndividualDayOfBirth when IndividualTemplate has #individualDayOfBirth and CharacterboxTemplate has #characterboxDayOfBirth')
	void "sets day of birth values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(dayOfBirth: characterboxDayOfBirth)
		IndividualTemplate individualTemplate = new IndividualTemplate(dayOfBirth: individualDayOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.dayOfBirth == expectedIndividualDayOfBirth

		where:
		individualDayOfBirth | characterboxDayOfBirth | expectedIndividualDayOfBirth
		null                 | DAY_1                  | DAY_1
		DAY_1                | DAY_1                  | DAY_1
		DAY_2                | DAY_1                  | DAY_2
		DAY_1                | null                   | DAY_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate place of birth to #expectedIndividualPlaceOfBirth when IndividualTemplate has #individualPlaceOfBirth and CharacterboxTemplate has #characterboxPlaceOfBirth')
	void "sets place of birth values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(placeOfBirth: characterboxPlaceOfBirth)
		IndividualTemplate individualTemplate = new IndividualTemplate(placeOfBirth: individualPlaceOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.placeOfBirth == expectedIndividualPlaceOfBirth

		where:
		individualPlaceOfBirth | characterboxPlaceOfBirth | expectedIndividualPlaceOfBirth
		null                   | PLACE_1                  | PLACE_1
		PLACE_1                | PLACE_1                  | PLACE_1
		PLACE_2                | PLACE_1                  | PLACE_2
		PLACE_1                | null                     | PLACE_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate year of death to #expectedIndividualYearOfDeath when IndividualTemplate has #individualYearOfDeath and CharacterboxTemplate has #characterboxYearOfDeath')
	void "sets year of death values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(yearOfDeath: characterboxYearOfDeath)
		IndividualTemplate individualTemplate = new IndividualTemplate(yearOfDeath: individualYearOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.yearOfDeath == expectedIndividualYearOfDeath

		where:
		individualYearOfDeath | characterboxYearOfDeath | expectedIndividualYearOfDeath
		null                  | YEAR_1                  | YEAR_1
		YEAR_1                | YEAR_1                  | YEAR_1
		YEAR_2                | YEAR_1                  | YEAR_2
		YEAR_1                | null                    | YEAR_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate month of death to #expectedIndividualMonthOfDeath when IndividualTemplate has #individualMonthOfDeath and CharacterboxTemplate has #characterboxMonthOfDeath')
	void "sets month of death values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(monthOfDeath: characterboxMonthOfDeath)
		IndividualTemplate individualTemplate = new IndividualTemplate(monthOfDeath: individualMonthOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.monthOfDeath == expectedIndividualMonthOfDeath

		where:
		individualMonthOfDeath | characterboxMonthOfDeath | expectedIndividualMonthOfDeath
		null                   | MONTH_1                  | MONTH_1
		MONTH_1                | MONTH_1                  | MONTH_1
		MONTH_2                | MONTH_1                  | MONTH_2
		MONTH_1                | null                     | MONTH_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate day of death to #expectedIndividualDayOfDeath when IndividualTemplate has #individualDayOfDeath and CharacterboxTemplate has #characterboxDayOfDeath')
	void "sets day of death values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(dayOfDeath: characterboxDayOfDeath)
		IndividualTemplate individualTemplate = new IndividualTemplate(dayOfDeath: individualDayOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.dayOfDeath == expectedIndividualDayOfDeath

		where:
		individualDayOfDeath | characterboxDayOfDeath | expectedIndividualDayOfDeath
		null                 | DAY_1                  | DAY_1
		DAY_1                | DAY_1                  | DAY_1
		DAY_2                | DAY_1                  | DAY_2
		DAY_1                | null                   | DAY_1
	}

	@SuppressWarnings('LineLength')
	@Unroll('sets IndividualTemplate place of death to #expectedIndividualPlaceOfDeath when IndividualTemplate has #individualPlaceOfDeath and CharacterboxTemplate has #characterboxPlaceOfDeath')
	void "sets place of death values of IndividualTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(placeOfDeath: characterboxPlaceOfDeath)
		IndividualTemplate individualTemplate = new IndividualTemplate(placeOfDeath: individualPlaceOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate))
		individualTemplate.placeOfDeath == expectedIndividualPlaceOfDeath

		where:
		individualPlaceOfDeath | characterboxPlaceOfDeath | expectedIndividualPlaceOfDeath
		null                   | PLACE_1                  | PLACE_1
		PLACE_1                | PLACE_1                  | PLACE_1
		PLACE_2                | PLACE_1                  | PLACE_2
		PLACE_1                | null                     | PLACE_1
	}

}
