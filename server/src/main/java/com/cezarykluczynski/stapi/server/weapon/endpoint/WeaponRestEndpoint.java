package com.cezarykluczynski.stapi.server.weapon.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.WeaponFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponRestReader;
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
public class WeaponRestEndpoint {

	public static final String ADDRESS = "/v1/rest/weapon";

	private final WeaponRestReader weaponRestReader;

	public WeaponRestEndpoint(WeaponRestReader weaponRestReader) {
		this.weaponRestReader = weaponRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public WeaponFullResponse getWeapon(@QueryParam("uid") String uid) {
		return weaponRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public WeaponBaseResponse searchWeapons(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return weaponRestReader.readBase(WeaponRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public WeaponBaseResponse searchWeapons(@BeanParam WeaponRestBeanParams weaponRestBeanParams) {
		return weaponRestReader.readBase(weaponRestBeanParams);
	}

}
