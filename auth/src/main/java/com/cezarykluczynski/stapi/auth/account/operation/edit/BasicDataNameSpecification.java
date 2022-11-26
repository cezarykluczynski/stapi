package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class BasicDataNameSpecification {

	boolean isSatisfiedBy(String name) {
		return name != null && name.length() >= 2;
	}

}
