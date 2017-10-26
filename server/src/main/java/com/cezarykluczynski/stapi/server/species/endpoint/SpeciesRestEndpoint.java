package com.cezarykluczynski.stapi.server.species.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader;
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
