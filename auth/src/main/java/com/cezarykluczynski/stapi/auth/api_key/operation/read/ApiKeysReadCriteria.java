package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeysReadCriteria {

	private Long accountId;

	private Long apiKeyId;

	private Integer pageNumber;

	private Integer pageSize;

	static ApiKeysReadCriteria ofAccountId(Long accountId) {
		return ApiKeysReadCriteria.builder()
				.accountId(accountId)
				.pageNumber(0)
				.build();
	}

	static ApiKeysReadCriteria ofPageNumberAndPageSize(int pageNumber, int pageSize) {
		return ApiKeysReadCriteria.builder()
				.pageNumber(pageNumber)
				.pageSize(pageSize)
				.build();
	}

}
