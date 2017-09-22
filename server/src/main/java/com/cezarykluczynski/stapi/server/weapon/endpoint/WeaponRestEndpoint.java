package com.cezarykluczynski.stapi.server.weapon.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponRestReader;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(MediaType.APPLICATION_JSON)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class WeaponRestEndpoint {

	public static final String ADDRESS = "/v1/rest/weapon";

	private final WeaponRestReader weaponRestReader;

	@Inject
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
