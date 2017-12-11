package com.cezarykluczynski.stapi.auth.account.operation.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
class AccountsReadCriteria {

	private Long id;

	private Long gitHubAccountId;

	private Integer pageNumber;

	private String name;

	private String email;

}
