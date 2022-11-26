package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class ApiKeyBlockOperation {

	private final ApiKeyBlockRelatedOperation apiKeyBlockRelatedOperation;

	public ApiKeyBlockOperation(ApiKeyBlockRelatedOperation apiKeyBlockRelatedOperation) {
		this.apiKeyBlockRelatedOperation = apiKeyBlockRelatedOperation;
	}

	public ApiKeyBlockRelatedResponseDTO execute(Long accountId, Long apiKeyId) {
		return apiKeyBlockRelatedOperation.execute(accountId, apiKeyId, true);
	}

}
