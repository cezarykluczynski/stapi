package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import info.bliki.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class BasicDataEmailSpecification {

	boolean isSatisfiedBy(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}
