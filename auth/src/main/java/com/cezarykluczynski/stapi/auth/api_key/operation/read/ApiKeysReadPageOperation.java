package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper;
import com.cezarykluczynski.stapi.auth.api_key.mapper.PagerMapper;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiKeysReadPageOperation {

	private final ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapper;

	private final ApiKeysReader apiKeysReader;

	private final ApiKeyMapper apiKeyMapper;

	private final PagerMapper pageMapper;

	private final ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactory;

	public ApiKeysReadPageOperation(ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapper, ApiKeysReader apiKeysReader, ApiKeyMapper apiKeyMapper,
			PagerMapper pageMapper, ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactory) {
		this.apiKeysReadCriteriaMapper = apiKeysReadCriteriaMapper;
		this.apiKeysReader = apiKeysReader;
		this.apiKeyMapper = apiKeyMapper;
		this.pageMapper = pageMapper;
		this.apiKeyReadResponseDTOFactory = apiKeyReadResponseDTOFactory;
	}

	public ApiKeyReadResponseDTO execute(ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO) {
		Page<ApiKey> apiKeyPage = apiKeysReader.execute(apiKeysReadCriteriaMapper.fromSearchCriteria(apiKeysSearchCriteriaDTO));
		List<ApiKeyDTO> apiKeyList = apiKeyPage.getContent().stream()
				.map(apiKeyMapper::map)
				.collect(Collectors.toList());
		Pager pager = pageMapper.map(apiKeyPage);
		return apiKeyReadResponseDTOFactory.createWithApiKeysAndPager(apiKeyList, pager);
	}

}
