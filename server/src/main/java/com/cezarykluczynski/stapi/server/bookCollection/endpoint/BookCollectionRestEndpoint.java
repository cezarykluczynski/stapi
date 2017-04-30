package com.cezarykluczynski.stapi.server.bookCollection.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.server.bookCollection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.bookCollection.reader.BookCollectionRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
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
public class BookCollectionRestEndpoint {

	public static final String ADDRESS = "/v1/rest/bookCollection";

	private BookCollectionRestReader bookCollectionRestReader;

	@Inject
	public BookCollectionRestEndpoint(BookCollectionRestReader bookCollectionRestReader) {
		this.bookCollectionRestReader = bookCollectionRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public BookCollectionFullResponse getBookCollection(@QueryParam("uid") String uid) {
		return bookCollectionRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public BookCollectionBaseResponse searchBookCollection(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return bookCollectionRestReader.readBase(BookCollectionRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public BookCollectionBaseResponse searchBookCollection(@BeanParam BookCollectionRestBeanParams bookCollectionRestBeanParams) {
		return bookCollectionRestReader.readBase(bookCollectionRestBeanParams);
	}

}
