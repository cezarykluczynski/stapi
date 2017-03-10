package com.cezarykluczynski.stapi.server.species.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/species")
@Produces(MediaType.APPLICATION_JSON)
public class SpeciesRestEndpoint {

	private SpeciesRestReader speciesRestReader;

	@Inject
	public SpeciesRestEndpoint(SpeciesRestReader speciesRestReader) {
		this.speciesRestReader = speciesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpeciesResponse getSpecies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return speciesRestReader.read(SpeciesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpeciesResponse searchSpecies(@BeanParam SpeciesRestBeanParams seriesRestBeanParams) {
		return speciesRestReader.read(seriesRestBeanParams);
	}

}
