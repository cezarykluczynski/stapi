package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams;
import com.cezarykluczynski.stapi.server.staff.reader.StaffV2RestReader;
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
