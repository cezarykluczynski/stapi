package com.cezarykluczynski.stapi.server.magazine.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MagazineFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine.reader.MagazineRestReader;
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
public class MagazineRestEndpoint {

	public static final String ADDRESS = "/v1/rest/magazine";

	private final MagazineRestReader magazineRestReader;

	public MagazineRestEndpoint(MagazineRestReader magazineRestReader) {
		this.magazineRestReader = magazineRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineFullResponse getMagazine(@QueryParam("uid") String uid) {
		return magazineRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineBaseResponse searchMagazine(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return magazineRestReader.readBase(MagazineRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MagazineBaseResponse searchMagazine(@BeanParam MagazineRestBeanParams magazineRestBeanParams) {
		return magazineRestReader.readBase(magazineRestBeanParams);
	}

}
