package com.cezarykluczynski.stapi.server.magazine.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine.reader.MagazineRestReader;
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
