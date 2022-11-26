package com.cezarykluczynski.stapi.auth.account.operation.removal;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class AccountRemovalOperation {

	private final AccountApi accountApi;

	private final AccountRemovalResponseDTOFactory accountRemovalResponseDTOFactory;

	public AccountRemovalOperation(AccountApi accountApi, AccountRemovalResponseDTOFactory accountRemovalResponseDTOFactory) {
		this.accountApi = accountApi;
		this.accountRemovalResponseDTOFactory = accountRemovalResponseDTOFactory;
	}

	public AccountRemovalResponseDTO execute(Long accountId) {
		try {
			accountApi.remove(accountId);
			return accountRemovalResponseDTOFactory.createSuccessful();
		} catch (Exception e) {
			return accountRemovalResponseDTOFactory.createUnsuccessful();
		}
	}

}
