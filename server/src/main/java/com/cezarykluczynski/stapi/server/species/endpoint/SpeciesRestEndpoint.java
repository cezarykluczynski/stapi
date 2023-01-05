package com.cezarykluczynski.stapi.server.species.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader;
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
public class SpeciesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/species";

	private final SpeciesRestReader speciesRestReader;

	public SpeciesRestEndpoint(SpeciesRestReader speciesRestReader) {
		this.speciesRestReader = speciesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpeciesFullResponse getSpecies(@QueryParam("uid") String uid) {
		return speciesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpeciesBaseResponse searchSpecies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return speciesRestReader.readBase(SpeciesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpeciesBaseResponse searchSpecies(@BeanParam SpeciesRestBeanParams speciesRestBeanParams) {
		return speciesRestReader.readBase(speciesRestBeanParams);
	}

}
