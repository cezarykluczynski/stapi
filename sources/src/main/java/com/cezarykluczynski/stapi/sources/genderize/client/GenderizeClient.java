package com.cezarykluczynski.stapi.sources.genderize.client;

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGenderDTO;

public interface GenderizeClient {

	NameGenderDTO getNameGender(String name);

}
