package com.cezarykluczynski.stapi.auth.api_key.operation;

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

	ApiKeysOperationsService(ApiKeysReadOperation apiKeysReadOperation, ApiKeyCreationOperation apiKeyCreationOperation,
			ApiKeyRemovalOperation apiKeyRemovalOperation) {
		this.apiKeysReadOperation = apiKeysReadOperation;
		this.apiKeyCreationOperation = apiKeyCreationOperation;
		this.apiKeyRemovalOperation = apiKeyRemovalOperation;
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

	public void deactive(Long accountId, Long keyId) {
		// TODO
	}

	public void activate(Long accountId, Long keyId) {
		// TODO
	}

	public void block(Long accountId, Long keyId) {
		// TODO
	}

	public void unblock(Long accountId, Long keyId) {
		// TODO
	}



}
