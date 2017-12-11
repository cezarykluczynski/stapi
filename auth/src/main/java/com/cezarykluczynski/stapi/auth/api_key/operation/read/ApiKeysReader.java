package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.common.factory.RequestSortDTOFactory;
import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties;
import com.cezarykluczynski.stapi.model.account.entity.Account_;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey_;
import com.cezarykluczynski.stapi.model.api_key.query.ApiKeyQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.google.common.base.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ApiKeysReader {

	private final ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory;

	private final ApiKeyProperties apiKeyProperties;

	private final RequestSortDTOFactory requestSortDTOFactory;

	ApiKeysReader(ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory, ApiKeyProperties apiKeyProperties,
			RequestSortDTOFactory requestSortDTOFactory) {
		this.apiKeyQueryBuilderFactory = apiKeyQueryBuilderFactory;
		this.apiKeyProperties = apiKeyProperties;
		this.requestSortDTOFactory = requestSortDTOFactory;
	}

	public Page<ApiKey> execute(ApiKeysReadCriteria criteria) {
		int pageNumber = Preconditions.checkNotNull(criteria.getPageNumber(), "Page number cannot be null");
		Pageable pageable = new PageRequest(pageNumber, apiKeyProperties.getAdminPageSize());
		Long accountId = criteria.getAccountId();
		Long apiKeyId = criteria.getApiKeyId();

		if (accountId == null && apiKeyId != null) {
			throw new IllegalStateException("Account ID cannot be null when API key isn't");
		}

		QueryBuilder<ApiKey> apiKeyQueryBuilder = apiKeyQueryBuilderFactory.createQueryBuilder(pageable);
		apiKeyQueryBuilder.equal(ApiKey_.id, apiKeyId);
		apiKeyQueryBuilder.equal(ApiKey_.accountId, accountId);
		apiKeyQueryBuilder.equal(ApiKey_.account, Account_.gitHubUserId, criteria.getGitHubAccountId());
		apiKeyQueryBuilder.like(ApiKey_.account, Account_.name, criteria.getName());
		apiKeyQueryBuilder.like(ApiKey_.account, Account_.email, criteria.getEmail());
		apiKeyQueryBuilder.like(ApiKey_.apiKey, criteria.getApiKey());
		apiKeyQueryBuilder.fetch(ApiKey_.throttle);
		apiKeyQueryBuilder.setSort(requestSortDTOFactory.create());
		return apiKeyQueryBuilder.findPage();
	}

}
