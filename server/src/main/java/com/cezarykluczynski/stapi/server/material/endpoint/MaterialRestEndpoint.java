package com.cezarykluczynski.stapi.server.material.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MaterialFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams;
import com.cezarykluczynski.stapi.server.material.reader.MaterialRestReader;
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
