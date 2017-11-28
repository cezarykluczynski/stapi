package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"MemberName", "ParameterName"})
public class AccountOwnOperationsService {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final AccountOperationsService accountOperationsService;

	private final AccountConsentTypesExtractor accountConsentTypesExtractor;

	public AccountOwnOperationsService(OAuthSessionHolder oAuthSessionHolder, AccountOperationsService accountOperationsService,
			AccountConsentTypesExtractor accountConsentTypesExtractor) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.accountOperationsService = accountOperationsService;
		this.accountConsentTypesExtractor = accountConsentTypesExtractor;
	}

	public AccountRemovalResponseDTO remove() {
		return accountOperationsService.remove(oAuthSessionHolder.getOAuthSession().getAccountId());
	}

	public AccountEditResponseDTO updateBasicData(String name, String email) {
		return accountOperationsService.updateBasicData(oAuthSessionHolder.getOAuthSession().getAccountId(), BasicDataDTO.builder()
				.name(name)
				.email(email)
				.build());
	}

	public AccountEditResponseDTO updateConsents(String[] consentTypes) {
		return accountOperationsService.updateConsents(oAuthSessionHolder.getOAuthSession().getAccountId(), accountConsentTypesExtractor
				.extract(consentTypes));
	}

	public AccountConsentsReadResponseDTO readConsents() {
		return accountOperationsService.readConsents(oAuthSessionHolder.getOAuthSession().getAccountId());
	}

}
