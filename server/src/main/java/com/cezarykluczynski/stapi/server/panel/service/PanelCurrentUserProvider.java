package com.cezarykluczynski.stapi.server.panel.service;

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO;
import com.cezarykluczynski.stapi.auth.account.mapper.UserDTOMapper;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
@SuppressWarnings({"MemberName", "ParameterName"})
public class PanelCurrentUserProvider {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final UserDTOMapper userDTOMapper;

	public PanelCurrentUserProvider(OAuthSessionHolder oAuthSessionHolder, UserDTOMapper userDTOMapper) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.userDTOMapper = userDTOMapper;
	}

	public UserDTO provide() {
		return userDTOMapper.map(oAuthSessionHolder.getOAuthSession());
	}

}
