package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"MemberName", "ParameterName"})
public class AccountOwnOperationsService {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final AccountOperationsService accountOperationsService;

	public AccountOwnOperationsService(OAuthSessionHolder oAuthSessionHolder, AccountOperationsService accountOperationsService) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.accountOperationsService = accountOperationsService;
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

}
