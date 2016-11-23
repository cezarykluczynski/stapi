package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/character")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterRestEndpoint {

	private CharacterRestReader characterRestReader;

	@Inject
	public CharacterRestEndpoint(CharacterRestReader characterRestReader) {
		this.characterRestReader = characterRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public CharacterResponse getCharacters(@BeanParam PageAwareBeanParams pageAwareBeanParams) {
		return characterRestReader.read(CharacterRestBeanParams.fromPageAwareBeanParams(pageAwareBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CharacterResponse searchCharacters(@BeanParam CharacterRestBeanParams seriesRestBeanParams) {
		return characterRestReader.read(seriesRestBeanParams);
	}

}
