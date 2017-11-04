package com.cezarykluczynski.stapi.auth.api_key.service;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiKeysOwnOperationsService {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final ApiKeysOperationsService apiKeysOperationsService;

	public ApiKeysOwnOperationsService(OAuthSessionHolder oAuthSessionHolder, ApiKeysOperationsService apiKeysOperationsService) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.apiKeysOperationsService = apiKeysOperationsService;
	}

	public List<ApiKeyDTO> getAll() {
		return apiKeysOperationsService.getAll(oAuthSessionHolder.getOAuthSession().getAccountId());
	}

}
