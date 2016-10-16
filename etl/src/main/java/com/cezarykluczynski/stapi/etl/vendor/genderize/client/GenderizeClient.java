package com.cezarykluczynski.stapi.etl.vendor.genderize.client;

import com.cezarykluczynski.stapi.etl.vendor.genderize.dto.NameGender;

public interface GenderizeClient {

	NameGender getNameGender(String name);

}
