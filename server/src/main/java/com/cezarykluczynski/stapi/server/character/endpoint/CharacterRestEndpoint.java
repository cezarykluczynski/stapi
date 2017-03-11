package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	public CharacterResponse getCharacters(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return characterRestReader.read(CharacterRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CharacterResponse searchCharacters(@BeanParam CharacterRestBeanParams seriesRestBeanParams) {
		return characterRestReader.read(seriesRestBeanParams);
	}

}
