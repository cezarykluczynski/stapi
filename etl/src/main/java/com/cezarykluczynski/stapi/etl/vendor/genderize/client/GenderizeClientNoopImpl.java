package com.cezarykluczynski.stapi.etl.vendor.genderize.client;

import com.cezarykluczynski.stapi.etl.vendor.genderize.dto.NameGender;
import com.cezarykluczynski.stapi.util.constant.SpringProfiles;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfiles.GENDERIZE_NOT)
public class GenderizeClientNoopImpl implements GenderizeClient {

	@Override
	public NameGender getNameGender(String name) {
		return null;
	}

}
