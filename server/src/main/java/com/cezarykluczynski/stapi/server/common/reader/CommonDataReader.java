package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.server.common.dataversion.CommonDataVersionProvider;
import com.cezarykluczynski.stapi.server.common.documentation.service.DocumentationProvider;
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class CommonDataReader {

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final StatisticsReader statisticsReader;

	private final DocumentationProvider documentationProvider;

	private final CommonDataVersionProvider commonDataVersionProvider;

	public CommonDataReader(CommonEntitiesStatisticsReader commonEntitiesStatisticsReader, CommonEntitiesDetailsReader commonEntitiesDetailsReader,
			StatisticsReader statisticsReader, DocumentationProvider documentationProvider, CommonDataVersionProvider commonDataVersionProvider) {
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
		this.statisticsReader = statisticsReader;
		this.documentationProvider = documentationProvider;
		this.commonDataVersionProvider = commonDataVersionProvider;
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

	public RestEndpointStatisticsDTO hitsStatistics() {
		return statisticsReader.hitsStatistics();
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

	public DataVersionDTO dataVersion() {
		return commonDataVersionProvider.provide();
	}

}
