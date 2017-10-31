package com.cezarykluczynski.stapi.sources.oauth.github.session;

import com.cezarykluczynski.stapi.sources.oauth.github.dto.UserDTO;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper {

	public UserDTO map(@SuppressWarnings("ParameterName") OAuthSession oAuthSession) {
		Preconditions.checkNotNull(oAuthSession, "oAuthSession cannot be null");
		UserDTO userDTO = new UserDTO();
		userDTO.setName(oAuthSession.getGitHubName());
		return userDTO;
	}

}
