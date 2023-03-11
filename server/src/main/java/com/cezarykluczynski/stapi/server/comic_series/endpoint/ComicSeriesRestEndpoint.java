package com.cezarykluczynski.stapi.server.comic_series.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_series.reader.ComicSeriesRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class ComicSeriesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/comicSeries";

	private final ComicSeriesRestReader comicSeriesRestReader;

	public ComicSeriesRestEndpoint(ComicSeriesRestReader comicSeriesRestReader) {
		this.comicSeriesRestReader = comicSeriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicSeriesFullResponse getComicSeries(@QueryParam("uid") String uid) {
		return comicSeriesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicSeriesBaseResponse searchComicSeries(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return comicSeriesRestReader.readBase(ComicSeriesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ComicSeriesBaseResponse searchComicSeries(@BeanParam ComicSeriesRestBeanParams comicSeriesRestBeanParams) {
		return comicSeriesRestReader.readBase(comicSeriesRestBeanParams);
	}

}
