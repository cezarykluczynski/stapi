package com.cezarykluczynski.stapi.auth.api_key.operation;

import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyUnblockOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO;
import org.springframework.stereotype.Service;

@Service
class ApiKeysOperationsService {

	private final ApiKeysReadOperation apiKeysReadOperation;

	private final ApiKeyCreationOperation apiKeyCreationOperation;

	private final ApiKeyRemovalOperation apiKeyRemovalOperation;

	private final ApiKeyBlockOperation apiKeyBlockOperation;

	private final ApiKeyUnblockOperation apiKeyUnblockOperation;

	ApiKeysOperationsService(ApiKeysReadOperation apiKeysReadOperation, ApiKeyCreationOperation apiKeyCreationOperation,
			ApiKeyRemovalOperation apiKeyRemovalOperation, ApiKeyBlockOperation apiKeyBlockOperation, ApiKeyUnblockOperation apiKeyUnblockOperation) {
		this.apiKeysReadOperation = apiKeysReadOperation;
		this.apiKeyCreationOperation = apiKeyCreationOperation;
		this.apiKeyRemovalOperation = apiKeyRemovalOperation;
		this.apiKeyBlockOperation = apiKeyBlockOperation;
		this.apiKeyUnblockOperation = apiKeyUnblockOperation;
	}

	public ApiKeyReadResponseDTO getAll(Long accountId) {
		return apiKeysReadOperation.execute(accountId);
	}

	public ApiKeyCreationResponseDTO create(Long accountId) {
		return apiKeyCreationOperation.execute(accountId);
	}

	public ApiKeyRemovalResponseDTO remove(Long accountId, Long apiKeyId) {
		return apiKeyRemovalOperation.execute(accountId, apiKeyId);
	}

	public ApiKeyBlockRelatedResponseDTO block(Long accountId, Long apiKeyId) {
		return apiKeyBlockOperation.execute(accountId, apiKeyId);
	}

	public ApiKeyBlockRelatedResponseDTO unblock(Long accountId, Long apiKeyId) {
		return apiKeyUnblockOperation.execute(accountId, apiKeyId);
	}

}
