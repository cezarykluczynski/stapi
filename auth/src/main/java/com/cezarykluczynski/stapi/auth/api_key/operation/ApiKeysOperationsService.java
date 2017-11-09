package com.cezarykluczynski.stapi.auth.api_key.operation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalOperation;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class ApiKeysOperationsService {

	private final ApiKeyCreationOperation apiKeyCreationOperation;

	private final ApiKeyRemovalOperation apiKeyRemovalOperation;

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyMapper apiKeyMapper;

	ApiKeysOperationsService(ApiKeyCreationOperation apiKeyCreationOperation, ApiKeyRemovalOperation apiKeyRemovalOperation,
			ApiKeyRepository apiKeyRepository, ApiKeyMapper apiKeyMapper) {
		this.apiKeyCreationOperation = apiKeyCreationOperation;
		this.apiKeyRemovalOperation = apiKeyRemovalOperation;
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyMapper = apiKeyMapper;
	}

	public List<ApiKeyDTO> getAll(Long accountId) {
		return apiKeyRepository.findAllByAccountId(accountId)
				.stream()
				.map(apiKeyMapper::map)
				.collect(Collectors.toList());
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
