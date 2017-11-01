package com.cezarykluczynski.stapi.server.panel.service;

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO;
import com.cezarykluczynski.stapi.auth.account.mapper.UserDTOMapper;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"MemberName", "ParameterName", "LocalVariableName"})
public class PanelCurrentUserProvider {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final UserDTOMapper userDTOMapper;

	public PanelCurrentUserProvider(OAuthSessionHolder oAuthSessionHolder, UserDTOMapper userDTOMapper) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.userDTOMapper = userDTOMapper;
	}

	public UserDTO provide() {
		OAuthSession oAuthSession = oAuthSessionHolder.getOAuthSession();

		if (oAuthSession == null) {
			throw new StapiRuntimeException("No session!");
		}

		return userDTOMapper.map(oAuthSession);
	}

}
