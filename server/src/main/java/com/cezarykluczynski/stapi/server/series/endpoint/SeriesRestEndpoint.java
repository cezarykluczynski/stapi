package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public SeriesFullResponse getSeries(@QueryParam("uid") String uid) {
		return seriesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SeriesBaseResponse searchSeries(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return seriesRestReader.readBase(SeriesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SeriesBaseResponse searchSeries(@BeanParam SeriesRestBeanParams seriesRestBeanParams) {
		return seriesRestReader.readBase(seriesRestBeanParams);
	}

}
