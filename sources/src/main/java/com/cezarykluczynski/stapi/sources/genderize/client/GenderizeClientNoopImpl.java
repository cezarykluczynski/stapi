package com.cezarykluczynski.stapi.sources.genderize.client;

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGender;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.GENDERIZE_NOT)
public class GenderizeClientNoopImpl implements GenderizeClient {

	@Override
	public NameGender getNameGender(String name) {
		return null;
	}

}
