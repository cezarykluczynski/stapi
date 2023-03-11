package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader;
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
public class StaffRestEndpoint {

	public static final String ADDRESS = "/v1/rest/staff";

	private final StaffRestReader staffRestReader;

	public StaffRestEndpoint(StaffRestReader staffRestReader) {
		this.staffRestReader = staffRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public StaffFullResponse getStaff(@QueryParam("uid") String uid) {
		return staffRestReader.readFull(uid);
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
