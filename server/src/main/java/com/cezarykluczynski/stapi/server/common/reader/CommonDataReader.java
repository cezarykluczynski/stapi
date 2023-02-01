package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dataversion.CommonDataVersionProvider;
import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.server.common.documentation.service.DocumentationProvider;
import com.cezarykluczynski.stapi.server.common.documentation.service.TosAttachmentProvider;
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class CommonDataReader {

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final DocumentationProvider documentationProvider;

	private final CommonDataVersionProvider commonDataVersionProvider;

	private final TosAttachmentProvider tosAttachmentProvider;

	public CommonDataReader(CommonEntitiesStatisticsReader commonEntitiesStatisticsReader, CommonEntitiesDetailsReader commonEntitiesDetailsReader,
			DocumentationProvider documentationProvider, CommonDataVersionProvider commonDataVersionProvider,
			TosAttachmentProvider tosAttachmentProvider) {
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
		this.documentationProvider = documentationProvider;
		this.commonDataVersionProvider = commonDataVersionProvider;
		this.tosAttachmentProvider = tosAttachmentProvider;
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

	public RestEndpointDetailsDTO details() {
		return commonEntitiesDetailsReader.details();
	}

	public DocumentationDTO documentation() {
		return documentationProvider.provideDocumentation();
	}

	public Response restSpecsZip() {
		return documentationProvider.provideRestSpecsZip();
	}

	public Response tosFormZip() {
		return tosAttachmentProvider.provide("form.docx");
	}

	public DataVersionDTO dataVersion() {
		return commonDataVersionProvider.provide();
	}

}
