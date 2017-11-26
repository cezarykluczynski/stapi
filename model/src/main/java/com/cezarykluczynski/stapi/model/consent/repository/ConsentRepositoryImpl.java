package com.cezarykluczynski.stapi.model.consent.repository;

import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsentRepositoryImpl implements ConsentRepositoryCustom {

	@Inject
	private ConsentRepository consentRepository;

	@Override
	public void ensureExists() {
		List<Consent> consentList = consentRepository.findAll();
		List<ConsentType> consentTypes = Lists.newArrayList(ConsentType.values());
		List<ConsentType> notPersistedConsentTypes = filterOutPersisted(consentList, consentTypes);
		consentRepository.save(notPersistedConsentTypes.stream()
				.map(this::create)
				.collect(Collectors.toList()));
	}

	private List<ConsentType> filterOutPersisted(List<Consent> consents, List<ConsentType> consentTypes) {
		Set<ConsentType> persistedConsentTypes = consents.stream()
				.map(Consent::getConsentType)
				.collect(Collectors.toSet());

		return consentTypes.stream()
				.filter(consentType -> !persistedConsentTypes.contains(consentType))
				.collect(Collectors.toList());
	}

	private Consent create(ConsentType consentType) {
		Consent consent = new Consent();
		consent.setConsentType(consentType);
		return consent;
	}

}
