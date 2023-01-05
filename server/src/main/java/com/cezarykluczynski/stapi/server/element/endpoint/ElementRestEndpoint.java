package com.cezarykluczynski.stapi.server.element.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams;
import com.cezarykluczynski.stapi.server.element.reader.ElementRestReader;
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
public class ElementRestEndpoint {

	public static final String ADDRESS = "/v1/rest/element";

	private final ElementRestReader elementRestReader;

	public ElementRestEndpoint(ElementRestReader elementRestReader) {
		this.elementRestReader = elementRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ElementFullResponse getElement(@QueryParam("uid") String uid) {
		return elementRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ElementBaseResponse searchElements(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return elementRestReader.readBase(ElementRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ElementBaseResponse searchElements(@BeanParam ElementRestBeanParams elementRestBeanParams) {
		return elementRestReader.readBase(elementRestBeanParams);
	}

}
