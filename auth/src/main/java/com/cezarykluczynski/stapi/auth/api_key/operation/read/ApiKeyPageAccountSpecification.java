package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class ApiKeyPageAccountSpecification {

	boolean isSatisfiedBy(Page<ApiKey> apiKeyPage) {
		return apiKeyPage.getTotalPages() == 1;
	}

}
