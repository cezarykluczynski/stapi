package com.cezarykluczynski.stapi.etl.location.creation.service;

import com.google.common.collect.Lists;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationNameFilter {

	private static final List<String> NOT_AN_LOCATIONS = Lists.newArrayList("Baldwin", "Door", "Geography", "San Francisco locations", "Flood");

	@SuppressFBWarnings("NP_BOOLEAN_RETURN_NULL")
	public Match isLocation(String organizationName) {
		return NOT_AN_LOCATIONS.contains(organizationName) ? Match.IS_NOT_A_LOCATION : Match.UNKNOWN_RESULT;
	}

	public enum Match {

		IS_NOT_A_LOCATION,
		UNKNOWN_RESULT

	}


}
