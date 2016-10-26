package com.cezarykluczynski.stapi.sources.genderize.client;

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGender;

public interface GenderizeClient {

	NameGender getNameGender(String name);

}
