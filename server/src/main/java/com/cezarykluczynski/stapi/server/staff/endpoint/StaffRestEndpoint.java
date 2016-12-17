package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Service
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
	public StaffResponse getStaffs(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return staffRestReader.read(StaffRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public StaffResponse searchStaffs(@BeanParam StaffRestBeanParams seriesRestBeanParams) {
		return staffRestReader.read(seriesRestBeanParams);
	}

}
