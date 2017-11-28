package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountConsentTypesExtractor {

	public Set<Consent> filterByType(List<Consent> consents, Set<ConsentType> consentTypes) {
		return consents.stream()
				.filter(consent -> consentTypes.contains(consent.getConsentType()))
				.collect(Collectors.toSet());
	}

	public Set<ConsentType> extract(String[] consentTypes) {
		return Lists.newArrayList(consentTypes).stream()
				.filter(StringUtils::isNotEmpty)
				.map(ConsentType::valueOf)
				.collect(Collectors.toSet());
	}

	public Set<ConsentType> extract(Account account) {
		return account.getConsents().stream()
				.map(Consent::getConsentType)
				.collect(Collectors.toSet());
	}

	public Set<String> extractAsStrings(Account account) {
		return account.getConsents().stream()
				.map(Consent::getConsentType)
				.map(ConsentType::name)
				.collect(Collectors.toSet());
	}

}
