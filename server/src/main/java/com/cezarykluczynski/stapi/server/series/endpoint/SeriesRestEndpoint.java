package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/series")
@Produces(MediaType.APPLICATION_JSON)
public class SeriesRestEndpoint {

	private SeriesRestReader seriesRestReader;

	@Inject
	public SeriesRestEndpoint(SeriesRestReader seriesRestReader) {
		this.seriesRestReader = seriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SeriesResponse getSeries(@BeanParam PageAwareBeanParams pageAwareBeanParams) {
		return seriesRestReader.read(SeriesRestBeanParams.fromPageAwareBeanParams(pageAwareBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SeriesResponse searchSeries(@BeanParam SeriesRestBeanParams seriesRestBeanParams) {
		return seriesRestReader.read(seriesRestBeanParams);
	}

}
