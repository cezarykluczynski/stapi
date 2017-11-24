package com.cezarykluczynski.stapi.auth.account.operation.edit;

import info.bliki.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
class BasicDataEmailSpecification {

	boolean isSatisfiedBy(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}
