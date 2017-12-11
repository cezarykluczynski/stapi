package com.cezarykluczynski.stapi.auth.api_key.operation;

import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyUnblockOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyDetailsDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadPageOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO;
import org.springframework.stereotype.Service;

@Service
class ApiKeysOperationsService {

	private final ApiKeysReadOperation apiKeysReadOperation;

	private final ApiKeysReadPageOperation apiKeysReadPageOperation;

	private final ApiKeyCreationOperation apiKeyCreationOperation;

	private final ApiKeyRemovalOperation apiKeyRemovalOperation;

	private final ApiKeyBlockOperation apiKeyBlockOperation;

	private final ApiKeyUnblockOperation apiKeyUnblockOperation;

	private final ApiKeyEditOperation apiKeyEditOperation;

	ApiKeysOperationsService(ApiKeysReadOperation apiKeysReadOperation, ApiKeysReadPageOperation apiKeysReadPageOperation,
			ApiKeyCreationOperation apiKeyCreationOperation, ApiKeyRemovalOperation apiKeyRemovalOperation, ApiKeyBlockOperation apiKeyBlockOperation,
			ApiKeyUnblockOperation apiKeyUnblockOperation, ApiKeyEditOperation apiKeyEditOperation) {
		this.apiKeysReadOperation = apiKeysReadOperation;
		this.apiKeysReadPageOperation = apiKeysReadPageOperation;
		this.apiKeyCreationOperation = apiKeyCreationOperation;
		this.apiKeyRemovalOperation = apiKeyRemovalOperation;
		this.apiKeyBlockOperation = apiKeyBlockOperation;
		this.apiKeyUnblockOperation = apiKeyUnblockOperation;
		this.apiKeyEditOperation = apiKeyEditOperation;
	}

	ApiKeyReadResponseDTO getAll(Long accountId) {
		return apiKeysReadOperation.execute(accountId);
	}

	ApiKeyReadResponseDTO search(ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO) {
		return apiKeysReadPageOperation.execute(apiKeysSearchCriteriaDTO);
	}

	ApiKeyCreationResponseDTO create(Long accountId) {
		return apiKeyCreationOperation.execute(accountId);
	}

	ApiKeyRemovalResponseDTO remove(Long accountId, Long apiKeyId) {
		return apiKeyRemovalOperation.execute(accountId, apiKeyId);
	}

	ApiKeyBlockRelatedResponseDTO block(Long accountId, Long apiKeyId) {
		return apiKeyBlockOperation.execute(accountId, apiKeyId);
	}

	ApiKeyBlockRelatedResponseDTO unblock(Long accountId, Long apiKeyId) {
		return apiKeyUnblockOperation.execute(accountId, apiKeyId);
	}

	ApiKeyEditResponseDTO edit(Long accountId, ApiKeyDetailsDTO apiKeyDetailsDTO) {
		return apiKeyEditOperation.execute(accountId, apiKeyDetailsDTO);
	}

}
