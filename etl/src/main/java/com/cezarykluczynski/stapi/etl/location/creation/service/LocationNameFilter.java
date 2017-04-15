package com.cezarykluczynski.stapi.etl.location.creation.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationNameFilter {

	private static final List<String> NOT_AN_LOCATIONS = Lists.newArrayList("Baldwin", "Door", "Geography", "San Francisco locations", "Flood");

	public Boolean isLocation(String organizationName) {
		return NOT_AN_LOCATIONS.contains(organizationName) ? false : null;
	}

}
