package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey_;
import com.cezarykluczynski.stapi.model.api_key.query.ApiKeyQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ApiKeysReader {

	private final ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory;

	private final ApiKeyProperties apiKeyProperties;

	ApiKeysReader(ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory, ApiKeyProperties apiKeyProperties) {
		this.apiKeyQueryBuilderFactory = apiKeyQueryBuilderFactory;
		this.apiKeyProperties = apiKeyProperties;
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
		apiKeyQueryBuilder.equal(ApiKey_.accountId, accountId);
		apiKeyQueryBuilder.equal(ApiKey_.id, apiKeyId);
		apiKeyQueryBuilder.fetch(ApiKey_.throttle);
		apiKeyQueryBuilder.setSort(createRequestSortDTO());
		return apiKeyQueryBuilder.findPage();
	}

	private static RequestSortDTO createRequestSortDTO() {
		RequestSortDTO requestSortDTO = new RequestSortDTO();
		RequestSortClauseDTO requestSortClauseDTO = new RequestSortClauseDTO();
		requestSortClauseDTO.setDirection(RequestSortDirectionDTO.ASC);
		requestSortClauseDTO.setName("id");
		requestSortDTO.setClauses(Lists.newArrayList(requestSortClauseDTO));
		return requestSortDTO;
	}

}
