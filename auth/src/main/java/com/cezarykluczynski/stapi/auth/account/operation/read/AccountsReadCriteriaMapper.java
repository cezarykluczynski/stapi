package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
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
