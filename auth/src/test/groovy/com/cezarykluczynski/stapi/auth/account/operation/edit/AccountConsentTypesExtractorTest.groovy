package com.cezarykluczynski.stapi.auth.account.operation.edit

import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.consent.entity.Consent
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class AccountConsentTypesExtractorTest extends Specification {

	private AccountConsentTypesExtractor accountConsentTypesExtractor

	void setup() {
		accountConsentTypesExtractor = new AccountConsentTypesExtractor()
	}

	void "filters consents by consent types"() {
		given:
		Consent consentToPreserve = new Consent(consentType: ConsentType.TECHNICAL_MAILING)
		Consent consentToFilterOut = new Consent()

		when:
		Set<Consent> consents = accountConsentTypesExtractor.filterByType(Lists.newArrayList(consentToPreserve, consentToFilterOut),
				Sets.newHashSet(ConsentType.TECHNICAL_MAILING))

		then:
		consents.size() == 1
		consents.contains consentToPreserve
	}

	void "extracts consent types from array of strings"() {
		given:
		String[] consentTypesString = new String[3]
		consentTypesString[0] = ''
		consentTypesString[1] = null
		consentTypesString[2] = ConsentType.TECHNICAL_MAILING.name()

		when:
		Set<ConsentType> consentTypes = accountConsentTypesExtractor.extract(consentTypesString)

		then:
		consentTypes.size() == 1
		consentTypes.contains ConsentType.TECHNICAL_MAILING
	}

	void "extracts consent types from account"() {
		given:
		Consent consent = new Consent(consentType: ConsentType.TECHNICAL_MAILING)
		Account account = new Account(consents: Sets.newHashSet(consent))

		when:
		Set<ConsentType> consentTypes = accountConsentTypesExtractor.extract(account)

		then:
		consentTypes.size() == 1
		consentTypes.contains ConsentType.TECHNICAL_MAILING
	}

	void "extracts consent types as string from account"() {
		given:
		Consent consent = new Consent(consentType: ConsentType.TECHNICAL_MAILING)
		Account account = new Account(consents: Sets.newHashSet(consent))

		when:
		Set<String> consentTypes = accountConsentTypesExtractor.extractAsStrings(account)

		then:
		consentTypes.size() == 1
		consentTypes.contains ConsentType.TECHNICAL_MAILING.name()
	}

}
