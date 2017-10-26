package com.cezarykluczynski.stapi.server.material.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams;
import com.cezarykluczynski.stapi.server.material.reader.MaterialRestReader;
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
public class MaterialRestEndpoint {

	public static final String ADDRESS = "/v1/rest/material";

	private final MaterialRestReader materialRestReader;

	public MaterialRestEndpoint(MaterialRestReader materialRestReader) {
		this.materialRestReader = materialRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MaterialFullResponse getMaterial(@QueryParam("uid") String uid) {
		return materialRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MaterialBaseResponse searchMaterials(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return materialRestReader.readBase(MaterialRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MaterialBaseResponse searchMaterials(@BeanParam MaterialRestBeanParams materialRestBeanParams) {
		return materialRestReader.readBase(materialRestBeanParams);
	}

}
