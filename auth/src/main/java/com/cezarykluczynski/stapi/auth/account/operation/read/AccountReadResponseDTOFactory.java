package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AccountReadResponseDTOFactory {

	AccountReadResponseDTO createWithAccountsAndPager(List<AccountDTO> accountList, Pager pager) {
		return AccountReadResponseDTO.builder()
				.successful(true)
				.accounts(accountList)
				.pager(pager)
				.build();
	}

}
