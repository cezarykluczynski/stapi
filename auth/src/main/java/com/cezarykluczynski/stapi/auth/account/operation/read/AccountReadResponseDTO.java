package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AccountReadResponseDTO {

	private boolean successful;

	private List<AccountDTO> accounts;

	private Pager pager;

}
