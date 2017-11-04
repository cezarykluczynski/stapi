package com.cezarykluczynski.stapi.auth.api_key.service;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiKeysOperationsService {

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyMapper apiKeyMapper;

	public ApiKeysOperationsService(ApiKeyRepository apiKeyRepository, ApiKeyMapper apiKeyMapper) {
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyMapper = apiKeyMapper;
	}

	public List<ApiKeyDTO> getAll(Long accountId) {
		return apiKeyRepository.findAllByAccountId(accountId)
				.stream()
				.map(apiKeyMapper::map)
				.collect(Collectors.toList());
	}

	public void create(Long accountId, Long keyId) {
		// TODO
	}

	public void delete(Long accountId, Long keyId) {
		// TODO
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
