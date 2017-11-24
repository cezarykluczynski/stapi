package com.cezarykluczynski.stapi.auth.account.mapper;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.auth.account.dto.UserDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDTOMapper {

	private final AccountApi accountApi;

	public UserDTOMapper(AccountApi accountApi) {
		this.accountApi = accountApi;
	}

	public UserDTO map(@SuppressWarnings("ParameterName") OAuthSession oAuthSession) {
		Optional<Account> accountOptional = accountApi.findByGitHubUserId(oAuthSession.getGitHubId());
		Account account = accountOptional.orElseThrow(() -> new StapiRuntimeException("Account not found!"));
		UserDTO userDTO = new UserDTO();
		userDTO.setName(account.getName());
		userDTO.setEmail(account.getEmail());
		userDTO.getPermissions().addAll(oAuthSession.getPermissions());
		return userDTO;
	}

}
