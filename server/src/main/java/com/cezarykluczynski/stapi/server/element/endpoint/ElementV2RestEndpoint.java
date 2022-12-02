package com.cezarykluczynski.stapi.server.element.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams;
import com.cezarykluczynski.stapi.server.element.reader.ElementV2RestReader;
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
public class ElementV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/element";

	private final ElementV2RestReader elementV2RestReader;

	public ElementV2RestEndpoint(ElementV2RestReader elementV2RestReader) {
		this.elementV2RestReader = elementV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ElementV2FullResponse getElement(@QueryParam("uid") String uid) {
		return elementV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ElementV2BaseResponse searchElements(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return elementV2RestReader.readBase(ElementV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ElementV2BaseResponse searchElements(@BeanParam ElementV2RestBeanParams elementV2RestBeanParams) {
		return elementV2RestReader.readBase(elementV2RestBeanParams);
	}

}
