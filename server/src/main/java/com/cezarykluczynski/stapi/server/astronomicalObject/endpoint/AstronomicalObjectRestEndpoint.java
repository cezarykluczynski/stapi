package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectResponse;
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("v1/rest/astronomicalObject")
@Produces(MediaType.APPLICATION_JSON)
public class AstronomicalObjectRestEndpoint {

	private AstronomicalObjectRestReader astronomicalObjectRestReader;

	@Inject
	public AstronomicalObjectRestEndpoint(AstronomicalObjectRestReader astronomicalObjectRestReader) {
		this.astronomicalObjectRestReader = astronomicalObjectRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public AstronomicalObjectResponse getAstronomicalObjects(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return astronomicalObjectRestReader.readBase(AstronomicalObjectRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public AstronomicalObjectResponse searchAstronomicalObjects(@BeanParam AstronomicalObjectRestBeanParams seriesRestBeanParams) {
		return astronomicalObjectRestReader.readBase(seriesRestBeanParams);
	}

}
