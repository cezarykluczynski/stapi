package com.cezarykluczynski.stapi.server.common.dto;

import com.google.common.collect.Sets;

import java.util.Set;

public class RestEndpointMappingsDTO {

	private String prefix;

	private Set<RestEndpointMappingDTO> urls = Sets.newHashSet();

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Set<RestEndpointMappingDTO> getUrls() {
		return urls;
	}

}
