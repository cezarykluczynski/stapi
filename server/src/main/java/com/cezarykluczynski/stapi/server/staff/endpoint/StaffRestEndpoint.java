package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("v1/rest/staff")
@Produces(MediaType.APPLICATION_JSON)
public class StaffRestEndpoint {

	private StaffRestReader staffRestReader;

	@Inject
	public StaffRestEndpoint(StaffRestReader staffRestReader) {
		this.staffRestReader = staffRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public StaffFullResponse getStaff(@QueryParam("guid") String guid) {
		return staffRestReader.readFull(guid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public StaffBaseResponse searchStaff(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return staffRestReader.readBase(StaffRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public StaffBaseResponse searchStaff(@BeanParam StaffRestBeanParams staffRestBeanParams) {
		return staffRestReader.readBase(staffRestBeanParams);
	}

}
