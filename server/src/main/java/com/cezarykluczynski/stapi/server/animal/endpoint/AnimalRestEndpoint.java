package com.cezarykluczynski.stapi.server.animal.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse;
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams;
import com.cezarykluczynski.stapi.server.animal.reader.AnimalRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
public class AnimalRestEndpoint {

	public static final String ADDRESS = "/v1/rest/animal";

	private final AnimalRestReader animalRestReader;

	public AnimalRestEndpoint(AnimalRestReader animalRestReader) {
		this.animalRestReader = animalRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public AnimalFullResponse getAnimal(@QueryParam("uid") String uid) {
		return animalRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public AnimalBaseResponse searchAnimals(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return animalRestReader.readBase(AnimalRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public AnimalBaseResponse searchAnimals(@BeanParam AnimalRestBeanParams animalRestBeanParams) {
		return animalRestReader.readBase(animalRestBeanParams);
	}

}
