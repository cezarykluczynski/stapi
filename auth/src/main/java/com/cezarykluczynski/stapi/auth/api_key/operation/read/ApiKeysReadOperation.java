package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ApiKeysReadOperation {

	private final ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapper;

	private final ApiKeysReader apiKeysReader;

	private final ApiKeyPageAccountSpecification apiKeyPageAccountSpecification;

	private final ApiKeyMapper apiKeyMapper;

	private final ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactory;

	public ApiKeysReadOperation(ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapper, ApiKeysReader apiKeysReader,
			ApiKeyPageAccountSpecification apiKeyPageAccountSpecification, ApiKeyMapper apiKeyMapper,
			ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactory) {
		this.apiKeysReadCriteriaMapper = apiKeysReadCriteriaMapper;
		this.apiKeyPageAccountSpecification = apiKeyPageAccountSpecification;
		this.apiKeysReader = apiKeysReader;
		this.apiKeyMapper = apiKeyMapper;
		this.apiKeyReadResponseDTOFactory = apiKeyReadResponseDTOFactory;
	}

	public ApiKeyReadResponseDTO execute(Long accountId) {
		Page<ApiKey> apiKeyPage = apiKeysReader.execute(apiKeysReadCriteriaMapper.fromAccountId(accountId));

		if (!apiKeyPageAccountSpecification.isSatisfiedBy(apiKeyPage)) {
			return apiKeyReadResponseDTOFactory.createFailedWithReason(ApiKeyReadResponseDTO.FailReason.TOO_MUCH_ENTRIES_FOR_A_SINGLE_PAGE);
		}

		return apiKeyReadResponseDTOFactory.createWithApiKeys(apiKeyPage.getContent().stream()
				.map(apiKeyMapper::map)
				.collect(Collectors.toList()));
	}

}
