package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class ApiKeysReadCriteriaMapper {

	ApiKeysReadCriteria fromAccountId(Long accountId) {
		return ApiKeysReadCriteria.builder()
				.accountId(accountId)
				.pageNumber(0)
				.build();
	}

	ApiKeysReadCriteria fromSearchCriteria(ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO) {
		return ApiKeysReadCriteria.builder()
				.pageNumber(apiKeysSearchCriteriaDTO.getPageNumber())
				.accountId(apiKeysSearchCriteriaDTO.getAccountId())
				.gitHubAccountId(apiKeysSearchCriteriaDTO.getGitHubAccountId())
				.name(apiKeysSearchCriteriaDTO.getName())
				.email(apiKeysSearchCriteriaDTO.getEmail())
				.apiKey(apiKeysSearchCriteriaDTO.getApiKey())
				.build();
	}

}
