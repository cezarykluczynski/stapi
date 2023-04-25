package com.cezarykluczynski.stapi.etl.genderize.client;

import com.cezarykluczynski.stapi.etl.genderize.dto.NameGenderDTO;

public interface GenderizeClient {

	NameGenderDTO getNameGender(String name);

}
