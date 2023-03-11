package com.cezarykluczynski.stapi.server.species.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesV2RestReader;
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
public class SpeciesV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/species";

	private final SpeciesV2RestReader speciesV2RestReader;

	public SpeciesV2RestEndpoint(SpeciesV2RestReader speciesV2RestReader) {
		this.speciesV2RestReader = speciesV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpeciesV2FullResponse getSpecies(@QueryParam("uid") String uid) {
		return speciesV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpeciesV2BaseResponse searchSpecies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return speciesV2RestReader.readBase(SpeciesV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpeciesV2BaseResponse searchSpecies(@BeanParam SpeciesV2RestBeanParams speciesRestBeanParams) {
		return speciesV2RestReader.readBase(speciesRestBeanParams);
	}

}
