package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.server.common.documentation.service.DocumentationProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class CommonDataReader {

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final CommonHitsStatisticsReader commonHitsStatisticsReader;

	private final DocumentationProvider documentationProvider;

	public CommonDataReader(CommonEntitiesStatisticsReader commonEntitiesStatisticsReader, CommonEntitiesDetailsReader commonEntitiesDetailsReader,
			CommonHitsStatisticsReader commonHitsStatisticsReader, DocumentationProvider documentationProvider) {
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
		this.commonHitsStatisticsReader = commonHitsStatisticsReader;
		this.documentationProvider = documentationProvider;
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

	public RestEndpointStatisticsDTO hitsStatistics() {
		return commonHitsStatisticsReader.hitsStatistics();
	}

	public RestEndpointDetailsDTO details() {
		return commonEntitiesDetailsReader.details();
	}

	public DocumentationDTO documentation() {
		return documentationProvider.provideDocumentation();
	}

	public Response soapContractsZip() {
		return documentationProvider.provideSoapContractsZip();
	}

	public Response restSpecsZip() {
		return documentationProvider.provideRestSpecsZip();
	}

}
