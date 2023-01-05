package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams;
import com.cezarykluczynski.stapi.server.staff.reader.StaffV2RestReader;
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
public class StaffV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/staff";

	private final StaffV2RestReader staffV2RestReader;

	public StaffV2RestEndpoint(StaffV2RestReader staffV2RestReader) {
		this.staffV2RestReader = staffV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public StaffV2FullResponse getStaff(@QueryParam("uid") String uid) {
		return staffV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public StaffV2BaseResponse searchStaff(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return staffV2RestReader.readBase(StaffV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public StaffV2BaseResponse searchStaff(@BeanParam StaffV2RestBeanParams staffV2RestBeanParams) {
		return staffV2RestReader.readBase(staffV2RestBeanParams);
	}

}
