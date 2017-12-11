package com.cezarykluczynski.stapi.auth.account.operation.read;

import org.springframework.stereotype.Service;

@Service
class AccountsReadCriteriaMapper {

	AccountsReadCriteria fromSearchCriteria(AccountsSearchCriteriaDTO accountsSearchCriteriaDTO) {
		return AccountsReadCriteria.builder()
				.id(accountsSearchCriteriaDTO.getId())
				.gitHubAccountId(accountsSearchCriteriaDTO.getGitHubAccountId())
				.name(accountsSearchCriteriaDTO.getName())
				.email(accountsSearchCriteriaDTO.getEmail())
				.pageNumber(accountsSearchCriteriaDTO.getPageNumber())
				.build();
	}

}
