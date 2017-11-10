package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ApiKeyReadResponseDTOFactory {

	ApiKeyReadResponseDTO createFailedWithReason(ApiKeyReadResponseDTO.FailReason failReason) {
		return ApiKeyReadResponseDTO.builder()
				.read(false)
				.failReason(failReason)
				.build();
	}

	ApiKeyReadResponseDTO createWithApiKeys(List<ApiKeyDTO> apiKeyDTOList) {
		return ApiKeyReadResponseDTO.builder()
				.read(true)
				.apiKeys(apiKeyDTOList)
				.build();
	}

	ApiKeyReadResponseDTO createWithApiKeysAndPager(List<ApiKeyDTO> apiKeys, Pager pager) {
		return ApiKeyReadResponseDTO.builder()
				.read(true)
				.apiKeys(apiKeys)
				.pager(pager)
				.build();
	}
}
