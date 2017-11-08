package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class ApiKeyWithThrottleDTO {

	private ApiKey apiKey;

	private Throttle throttle;

}
