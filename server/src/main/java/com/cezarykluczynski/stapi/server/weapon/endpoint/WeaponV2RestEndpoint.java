package com.cezarykluczynski.stapi.server.weapon.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.WeaponV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponV2RestReader;
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
public class WeaponV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/weapon";

	private final WeaponV2RestReader weaponV2RestReader;

	public WeaponV2RestEndpoint(WeaponV2RestReader weaponV2RestReader) {
		this.weaponV2RestReader = weaponV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public WeaponV2FullResponse getWeapon(@QueryParam("uid") String uid) {
		return weaponV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public WeaponV2BaseResponse searchWeapons(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return weaponV2RestReader.readBase(WeaponV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public WeaponV2BaseResponse searchWeapons(@BeanParam WeaponV2RestBeanParams weaponV2RestBeanParams) {
		return weaponV2RestReader.readBase(weaponV2RestBeanParams);
	}

}
