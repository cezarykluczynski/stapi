package com.cezarykluczynski.stapi.server.comicCollection.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("v1/rest/comicCollection")
@Produces(MediaType.APPLICATION_JSON)
public class ComicCollectionRestEndpoint {

	private ComicCollectionRestReader comicCollectionRestReader;

	@Inject
	public ComicCollectionRestEndpoint(ComicCollectionRestReader comicCollectionRestReader) {
		this.comicCollectionRestReader = comicCollectionRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicCollectionFullResponse getComicCollection(@QueryParam("guid") String guid) {
		return comicCollectionRestReader.readFull(guid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicCollectionBaseResponse searchComicCollection(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return comicCollectionRestReader.readBase(ComicCollectionRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ComicCollectionBaseResponse searchComicCollection(@BeanParam ComicCollectionRestBeanParams comicCollectionRestBeanParams) {
		return comicCollectionRestReader.readBase(comicCollectionRestBeanParams);
	}

}
