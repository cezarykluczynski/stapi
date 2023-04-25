package com.cezarykluczynski.stapi.etl.genderize.client;

import com.cezarykluczynski.stapi.etl.genderize.dto.NameGenderDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.GENDERIZE_NOT)
public class GenderizeClientNoopImpl implements GenderizeClient {

	@Override
	public NameGenderDTO getNameGender(String name) {
		return null;
	}

}
