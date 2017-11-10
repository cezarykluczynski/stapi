package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
class ApiKeyPageAccountSpecification {

	boolean isSatisfiedBy(Page<ApiKey> apiKeyPage) {
		return apiKeyPage.getTotalPages() == 1;
	}

}
