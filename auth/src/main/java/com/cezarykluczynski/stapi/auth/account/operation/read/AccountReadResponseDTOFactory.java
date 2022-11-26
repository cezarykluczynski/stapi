package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(SpringProfile.AUTH)
class AccountReadResponseDTOFactory {

	AccountReadResponseDTO createWithAccountsAndPager(List<AccountDTO> accountList, Pager pager) {
		return AccountReadResponseDTO.builder()
				.successful(true)
				.accounts(accountList)
				.pager(pager)
				.build();
	}

}
