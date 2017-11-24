package com.cezarykluczynski.stapi.auth.account.operation.edit;

import org.springframework.stereotype.Service;

@Service
class BasicDataNameSpecification {

	boolean isSatisfiedBy(String name) {
		return name != null && name.length() >= 2;
	}

}
