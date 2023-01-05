package com.cezarykluczynski.stapi.server.season.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams;
import com.cezarykluczynski.stapi.server.season.reader.SeasonRestReader;
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
public class SeasonRestEndpoint {

	public static final String ADDRESS = "/v1/rest/season";

	private final SeasonRestReader seasonRestReader;

	public SeasonRestEndpoint(SeasonRestReader seasonRestReader) {
		this.seasonRestReader = seasonRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SeasonFullResponse getSeason(@QueryParam("uid") String uid) {
		return seasonRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SeasonBaseResponse searchSeasons(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return seasonRestReader.readBase(SeasonRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SeasonBaseResponse searchSeasons(@BeanParam SeasonRestBeanParams seasonRestBeanParams) {
		return seasonRestReader.readBase(seasonRestBeanParams);
	}

}
