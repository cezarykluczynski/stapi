package com.cezarykluczynski.stapi.auth.api_key.operation;

import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyDetailsDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import org.springframework.stereotype.Service;

@Service
public class ApiKeysOwnOperationsService {

	private final OAuthSessionHolder oAuthSessionHolder;

	private final ApiKeysOperationsService apiKeysOperationsService;

	public ApiKeysOwnOperationsService(OAuthSessionHolder oAuthSessionHolder, ApiKeysOperationsService apiKeysOperationsService) {
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.apiKeysOperationsService = apiKeysOperationsService;
	}

	public ApiKeyReadResponseDTO getAll() {
		return apiKeysOperationsService.getAll(oAuthSessionHolder.getOAuthSession().getAccountId());
	}

	public ApiKeyCreationResponseDTO create() {
		return apiKeysOperationsService.create(oAuthSessionHolder.getOAuthSession().getAccountId());
	}

	public ApiKeyRemovalResponseDTO remove(Long apiKeyId) {
		return apiKeysOperationsService.remove(oAuthSessionHolder.getOAuthSession().getAccountId(), apiKeyId);
	}

	public ApiKeyEditResponseDTO edit(Long apiKeyId, String url, String description) {
		return apiKeysOperationsService.edit(oAuthSessionHolder.getOAuthSession().getAccountId(), ApiKeyDetailsDTO.builder()
				.apiKeyId(apiKeyId)
				.url(url)
				.description(description)
				.build());
	}
}
