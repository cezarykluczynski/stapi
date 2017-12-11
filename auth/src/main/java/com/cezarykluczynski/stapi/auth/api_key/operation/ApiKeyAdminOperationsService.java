package com.cezarykluczynski.stapi.auth.api_key.operation;

import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyAdminOperationsService {

	private final ApiKeysOperationsService apiKeysOperationsService;

	public ApiKeyAdminOperationsService(ApiKeysOperationsService apiKeysOperationsService) {
		this.apiKeysOperationsService = apiKeysOperationsService;
	}

	public ApiKeyReadResponseDTO search(ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO) {
		return apiKeysOperationsService.search(apiKeysSearchCriteriaDTO);
	}

	public ApiKeyBlockRelatedResponseDTO block(Long accountId, Long apiKeyId) {
		return apiKeysOperationsService.block(accountId, apiKeyId);
	}

	public ApiKeyBlockRelatedResponseDTO unblock(Long accountId, Long apiKeyId) {
		return apiKeysOperationsService.unblock(accountId, apiKeyId);
	}

}
