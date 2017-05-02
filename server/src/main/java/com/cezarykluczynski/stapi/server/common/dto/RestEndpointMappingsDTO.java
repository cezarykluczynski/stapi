package com.cezarykluczynski.stapi.server.common.dto;

import com.google.common.collect.Sets;

import java.util.Set;

public class RestEndpointMappingsDTO {

	private Set<RestEndpointMappingDTO> urls = Sets.newHashSet();

	public Set<RestEndpointMappingDTO> getUrls() {
		return urls;
	}

}
