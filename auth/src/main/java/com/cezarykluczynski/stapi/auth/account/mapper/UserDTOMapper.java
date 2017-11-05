package com.cezarykluczynski.stapi.auth.account.mapper;

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper {

	public UserDTO map(@SuppressWarnings("ParameterName") OAuthSession oAuthSession) {
		Preconditions.checkNotNull(oAuthSession, "oAuthSession cannot be null");
		UserDTO userDTO = new UserDTO();
		userDTO.setName(oAuthSession.getGitHubName());
		userDTO.getPermissions().addAll(oAuthSession.getPermissions());
		return userDTO;
	}

}
