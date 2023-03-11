package com.cezarykluczynski.stapi.server.magazine_series.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine_series.reader.MagazineSeriesRestReader;
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
public class MagazineSeriesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/magazineSeries";

	private final MagazineSeriesRestReader magazineSeriesRestReader;

	public MagazineSeriesRestEndpoint(MagazineSeriesRestReader magazineSeriesRestReader) {
		this.magazineSeriesRestReader = magazineSeriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineSeriesFullResponse getMagazineSeries(@QueryParam("uid") String uid) {
		return magazineSeriesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineSeriesBaseResponse searchMagazineSeries(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return magazineSeriesRestReader.readBase(MagazineSeriesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MagazineSeriesBaseResponse searchMagazineSeries(@BeanParam MagazineSeriesRestBeanParams magazineSeriesRestBeanParams) {
		return magazineSeriesRestReader.readBase(magazineSeriesRestBeanParams);
	}

}
