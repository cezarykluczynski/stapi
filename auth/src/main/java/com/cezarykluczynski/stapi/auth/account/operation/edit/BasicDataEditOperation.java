package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasicDataEditOperation {

	private final AccountApi accountApi;

	private final BasicDataNameSpecification basicDataNameSpecification;

	private final BasicDataEmailSpecification basicDataEmailSpecification;

	private final AccountEditResponseDTOFactory accountEditResponseDTOFactory;

	public BasicDataEditOperation(AccountApi accountApi, BasicDataNameSpecification basicDataNameSpecification,
			BasicDataEmailSpecification basicDataEmailSpecification, AccountEditResponseDTOFactory accountEditResponseDTOFactory) {
		this.accountApi = accountApi;
		this.basicDataNameSpecification = basicDataNameSpecification;
		this.basicDataEmailSpecification = basicDataEmailSpecification;
		this.accountEditResponseDTOFactory = accountEditResponseDTOFactory;
	}

	@Transactional
	public AccountEditResponseDTO execute(Long accountId, BasicDataDTO basicDataDTO) {
		Preconditions.checkNotNull(accountId, "Account ID cannot be null");
		Preconditions.checkNotNull(basicDataDTO, "BasicDataDTO cannot be null");
		Account account = accountApi.findById(accountId).orElseThrow(() -> new StapiRuntimeException("Account not found!"));
		String incomingName = basicDataDTO.getName();
		String incomingEmail = basicDataDTO.getEmail();
		boolean nameChanged = !StringUtils.equals(incomingName, account.getName());
		boolean emailChanged = !StringUtils.equals(incomingEmail, account.getEmail());

		if (!nameChanged && !emailChanged) {
			return accountEditResponseDTOFactory.createUnchanged();
		}

		if (nameChanged && !basicDataNameSpecification.isSatisfiedBy(incomingName)) {
			return accountEditResponseDTOFactory.createUnsuccessful(AccountEditResponseDTO.FailReason.INVALID_NAME);
		}
		if (emailChanged && !basicDataEmailSpecification.isSatisfiedBy(incomingEmail)) {
			return accountEditResponseDTOFactory.createUnsuccessful(AccountEditResponseDTO.FailReason.INVALID_EMAIL);
		}

		account.setName(incomingName);
		account.setEmail(incomingEmail);

		return accountEditResponseDTOFactory.createSuccessful();
	}


}
