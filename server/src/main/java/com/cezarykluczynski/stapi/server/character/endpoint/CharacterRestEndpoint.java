package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.CharacterFullResponse;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
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
