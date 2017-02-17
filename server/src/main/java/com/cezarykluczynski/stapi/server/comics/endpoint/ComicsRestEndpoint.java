package com.cezarykluczynski.stapi.server.comics.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsResponse;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.reader.ComicsRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/comics")
@Produces(MediaType.APPLICATION_JSON)
public class ComicsRestEndpoint {

	private ComicsRestReader comicsRestReader;

	@Inject
	public ComicsRestEndpoint(ComicsRestReader comicsRestReader) {
		this.comicsRestReader = comicsRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicsResponse getComics(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return comicsRestReader.read(ComicsRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ComicsResponse searchComics(@BeanParam ComicsRestBeanParams seriesRestBeanParams) {
		return comicsRestReader.read(seriesRestBeanParams);
	}

}
