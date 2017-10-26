package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
public class CharacterRestEndpoint {

	public static final String ADDRESS = "/v1/rest/character";

	private final CharacterRestReader characterRestReader;

	public CharacterRestEndpoint(CharacterRestReader characterRestReader) {
		this.characterRestReader = characterRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public CharacterFullResponse getCharacter(@QueryParam("uid") String uid) {
		return characterRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public CharacterBaseResponse searchCharacter(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return characterRestReader.readBase(CharacterRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CharacterBaseResponse searchCharacter(@BeanParam CharacterRestBeanParams characterRestBeanParams) {
		return characterRestReader.readBase(characterRestBeanParams);
	}

}
