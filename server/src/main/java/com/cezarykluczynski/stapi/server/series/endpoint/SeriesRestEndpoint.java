package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class SeriesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/series";

	private final SeriesRestReader seriesRestReader;

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
