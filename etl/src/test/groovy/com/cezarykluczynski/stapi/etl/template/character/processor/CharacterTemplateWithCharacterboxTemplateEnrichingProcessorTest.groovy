package com.cezarykluczynski.stapi.etl.template.character.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import spock.lang.Specification
import spock.lang.Unroll

class CharacterTemplateWithCharacterboxTemplateEnrichingProcessorTest extends Specification {

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

	private CharacterTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessor

	void setup() {
		individualTemplateWithCharacterboxTemplateEnrichingProcessor = new CharacterTemplateWithCharacterboxTemplateEnrichingProcessor()
	}

	@Unroll('''sets CharacterTemplate gender to #expectedIndividualGender when CharacterTemplate has
		#individualGender and CharacterboxTemplate has #characterboxGender''')
	void "sets gender values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(gender: characterboxGender)
		CharacterTemplate characterTemplate = new CharacterTemplate(gender: individualGender)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.gender == expectedIndividualGender

		where:
		individualGender | characterboxGender | expectedIndividualGender
		null             | GENDER_F           | GENDER_F
		GENDER_F         | GENDER_F           | GENDER_F
		GENDER_M         | GENDER_F           | GENDER_M
		GENDER_F         | null               | GENDER_F
	}

	@Unroll('''sets CharacterTemplate weight to #expectedIndividualWeight when CharacterTemplate has
		#individualWeight and CharacterboxTemplate has #characterboxWeight''')
	void "sets weight values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(weight: characterboxWeight)
		CharacterTemplate characterTemplate = new CharacterTemplate(weight: individualWeight)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.weight == expectedIndividualWeight

		where:
		individualWeight | characterboxWeight | expectedIndividualWeight
		null             | WEIGHT_1           | WEIGHT_1
		WEIGHT_1         | WEIGHT_1           | WEIGHT_1
		WEIGHT_2         | WEIGHT_1           | WEIGHT_2
		WEIGHT_1         | null               | WEIGHT_1
	}

	@Unroll('''sets CharacterTemplate height to #expectedIndividualHeight when CharacterTemplate has
		#individualHeight and CharacterboxTemplate has #characterboxHeight''')
	void "sets height values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(height: characterboxHeight)
		CharacterTemplate characterTemplate = new CharacterTemplate(height: individualHeight)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.height == expectedIndividualHeight

		where:
		individualHeight | characterboxHeight | expectedIndividualHeight
		null             | HEIGHT_1           | HEIGHT_1
		HEIGHT_1         | HEIGHT_1           | HEIGHT_1
		HEIGHT_2         | HEIGHT_1           | HEIGHT_2
		HEIGHT_1         | null               | HEIGHT_1
	}

	@Unroll('''sets CharacterTemplate marital status to #expectedIndividualMaritalStatus when CharacterTemplate has
		#individualMaritalStatus and CharacterboxTemplate has #characterboxMaritalStatus''')
	void "sets marital status values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(maritalStatus: characterboxMaritalStatus)
		CharacterTemplate characterTemplate = new CharacterTemplate(maritalStatus: individualMaritalStatus)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.maritalStatus == expectedIndividualMaritalStatus

		where:
		individualMaritalStatus | characterboxMaritalStatus | expectedIndividualMaritalStatus
		null                    | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_MARRIED
		MARITAL_STATUS_MARRIED  | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_MARRIED
		MARITAL_STATUS_DIVORCED | MARITAL_STATUS_MARRIED    | MARITAL_STATUS_DIVORCED
		MARITAL_STATUS_MARRIED  | null                      | MARITAL_STATUS_MARRIED
	}

	@Unroll('''sets CharacterTemplate year of birth to #expectedIndividualYearOfBirth when CharacterTemplate has
		#individualYearOfBirth and CharacterboxTemplate has #characterboxYearOfBirth''')
	void "sets year of birth values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(yearOfBirth: characterboxYearOfBirth)
		CharacterTemplate characterTemplate = new CharacterTemplate(yearOfBirth: individualYearOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.yearOfBirth == expectedIndividualYearOfBirth

		where:
		individualYearOfBirth | characterboxYearOfBirth | expectedIndividualYearOfBirth
		null                  | YEAR_1                  | YEAR_1
		YEAR_1                | YEAR_1                  | YEAR_1
		YEAR_2                | YEAR_1                  | YEAR_2
		YEAR_1                | null                    | YEAR_1
	}

	@Unroll('''sets CharacterTemplate month of birth to #expectedIndividualMonthOfBirth when CharacterTemplate has
		#individualMonthOfBirth and CharacterboxTemplate has #characterboxMonthOfBirth''')
	void "sets month of birth values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(monthOfBirth: characterboxMonthOfBirth)
		CharacterTemplate characterTemplate = new CharacterTemplate(monthOfBirth: individualMonthOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.monthOfBirth == expectedIndividualMonthOfBirth

		where:
		individualMonthOfBirth | characterboxMonthOfBirth | expectedIndividualMonthOfBirth
		null                   | MONTH_1                  | MONTH_1
		MONTH_1                | MONTH_1                  | MONTH_1
		MONTH_2                | MONTH_1                  | MONTH_2
		MONTH_1                | null                     | MONTH_1
	}

	@Unroll('''sets CharacterTemplate day of birth to #expectedIndividualDayOfBirth when CharacterTemplate has
#individualDayOfBirth and CharacterboxTemplate has #characterboxDayOfBirth''')
	void "sets day of birth values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(dayOfBirth: characterboxDayOfBirth)
		CharacterTemplate characterTemplate = new CharacterTemplate(dayOfBirth: individualDayOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.dayOfBirth == expectedIndividualDayOfBirth

		where:
		individualDayOfBirth | characterboxDayOfBirth | expectedIndividualDayOfBirth
		null                 | DAY_1                  | DAY_1
		DAY_1                | DAY_1                  | DAY_1
		DAY_2                | DAY_1                  | DAY_2
		DAY_1                | null                   | DAY_1
	}

	@Unroll('''sets CharacterTemplate place of birth to #expectedIndividualPlaceOfBirth when CharacterTemplate has
		#individualPlaceOfBirth and CharacterboxTemplate has #characterboxPlaceOfBirth''')
	void "sets place of birth values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(placeOfBirth: characterboxPlaceOfBirth)
		CharacterTemplate characterTemplate = new CharacterTemplate(placeOfBirth: individualPlaceOfBirth)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.placeOfBirth == expectedIndividualPlaceOfBirth

		where:
		individualPlaceOfBirth | characterboxPlaceOfBirth | expectedIndividualPlaceOfBirth
		null                   | PLACE_1                  | PLACE_1
		PLACE_1                | PLACE_1                  | PLACE_1
		PLACE_2                | PLACE_1                  | PLACE_2
		PLACE_1                | null                     | PLACE_1
	}

	@Unroll('''sets CharacterTemplate year of death to #expectedIndividualYearOfDeath when CharacterTemplate has
		#individualYearOfDeath and CharacterboxTemplate has #characterboxYearOfDeath''')
	void "sets year of death values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(yearOfDeath: characterboxYearOfDeath)
		CharacterTemplate characterTemplate = new CharacterTemplate(yearOfDeath: individualYearOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.yearOfDeath == expectedIndividualYearOfDeath

		where:
		individualYearOfDeath | characterboxYearOfDeath | expectedIndividualYearOfDeath
		null                  | YEAR_1                  | YEAR_1
		YEAR_1                | YEAR_1                  | YEAR_1
		YEAR_2                | YEAR_1                  | YEAR_2
		YEAR_1                | null                    | YEAR_1
	}

	@Unroll('''sets CharacterTemplate month of death to #expectedIndividualMonthOfDeath when CharacterTemplate has
		#individualMonthOfDeath and CharacterboxTemplate has #characterboxMonthOfDeath''')
	void "sets month of death values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(monthOfDeath: characterboxMonthOfDeath)
		CharacterTemplate characterTemplate = new CharacterTemplate(monthOfDeath: individualMonthOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.monthOfDeath == expectedIndividualMonthOfDeath

		where:
		individualMonthOfDeath | characterboxMonthOfDeath | expectedIndividualMonthOfDeath
		null                   | MONTH_1                  | MONTH_1
		MONTH_1                | MONTH_1                  | MONTH_1
		MONTH_2                | MONTH_1                  | MONTH_2
		MONTH_1                | null                     | MONTH_1
	}

	@Unroll('''sets CharacterTemplate day of death to #expectedIndividualDayOfDeath when CharacterTemplate has
		#individualDayOfDeath and CharacterboxTemplate has #characterboxDayOfDeath''')
	void "sets day of death values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(dayOfDeath: characterboxDayOfDeath)
		CharacterTemplate characterTemplate = new CharacterTemplate(dayOfDeath: individualDayOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.dayOfDeath == expectedIndividualDayOfDeath

		where:
		individualDayOfDeath | characterboxDayOfDeath | expectedIndividualDayOfDeath
		null                 | DAY_1                  | DAY_1
		DAY_1                | DAY_1                  | DAY_1
		DAY_2                | DAY_1                  | DAY_2
		DAY_1                | null                   | DAY_1
	}

	@Unroll('''sets CharacterTemplate place of death to #expectedIndividualPlaceOfDeath when CharacterTemplate has
		#individualPlaceOfDeath and CharacterboxTemplate has #characterboxPlaceOfDeath''')
	void "sets place of death values of CharacterTemplate with values of CharacterboxTemplate"() {
		given:
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate(placeOfDeath: characterboxPlaceOfDeath)
		CharacterTemplate characterTemplate = new CharacterTemplate(placeOfDeath: individualPlaceOfDeath)

		expect:
		individualTemplateWithCharacterboxTemplateEnrichingProcessor.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate))
		characterTemplate.placeOfDeath == expectedIndividualPlaceOfDeath

		where:
		individualPlaceOfDeath | characterboxPlaceOfDeath | expectedIndividualPlaceOfDeath
		null                   | PLACE_1                  | PLACE_1
		PLACE_1                | PLACE_1                  | PLACE_1
		PLACE_2                | PLACE_1                  | PLACE_2
		PLACE_1                | null                     | PLACE_1
	}

}
