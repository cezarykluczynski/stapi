package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.google.common.base.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ApiKeysReader {

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyProperties apiKeyProperties;

	ApiKeysReader(ApiKeyRepository apiKeyRepository, ApiKeyProperties apiKeyProperties) {
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyProperties = apiKeyProperties;
	}

	public Page<ApiKey> execute(ApiKeysReadCriteria criteria) {
		int pageNumber = Preconditions.checkNotNull(criteria.getPageNumber(), "Page number cannot be null");
		Pageable pageable = new PageRequest(pageNumber, apiKeyProperties.getAdminPageSize());
		Long accountId = criteria.getAccountId();
		Long apiKeyId = criteria.getApiKeyId();
		if (accountId == null && apiKeyId == null) {
			return apiKeyRepository.findAll(pageable);
		} else if (accountId != null && apiKeyId == null) {
			return apiKeyRepository.findAllByAccountId(accountId, pageable);
		} else if (accountId != null) {
			return apiKeyRepository.findAllByAccountIdAndId(accountId, apiKeyId, pageable);
		} else {
			throw new IllegalStateException("Account ID cannot be null when API key isn't");
		}
	}

}
