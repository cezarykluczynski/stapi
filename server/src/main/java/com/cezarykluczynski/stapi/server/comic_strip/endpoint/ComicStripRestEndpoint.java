package com.cezarykluczynski.stapi.server.comic_strip.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse;
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_strip.reader.ComicStripRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
public class ComicStripRestEndpoint {

	public static final String ADDRESS = "/v1/rest/comicStrip";

	private final ComicStripRestReader comicStripRestReader;

	public ComicStripRestEndpoint(ComicStripRestReader comicStripRestReader) {
		this.comicStripRestReader = comicStripRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicStripFullResponse getComicStrip(@QueryParam("uid") String uid) {
		return comicStripRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicStripBaseResponse searchComicStrip(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return comicStripRestReader.readBase(ComicStripRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ComicStripBaseResponse searchComicStrip(@BeanParam ComicStripRestBeanParams comicStripRestBeanParams) {
		return comicStripRestReader.readBase(comicStripRestBeanParams);
	}

}
