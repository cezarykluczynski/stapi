package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CommonDataReader {

	private final CommonMappingsReader commonMappingsReader;

	@Inject
	public CommonDataReader(CommonMappingsReader commonMappingsReader) {
		this.commonMappingsReader = commonMappingsReader;
	}

	public RestEndpointMappingsDTO mappings() {
		return commonMappingsReader.mappings();
	}

}
