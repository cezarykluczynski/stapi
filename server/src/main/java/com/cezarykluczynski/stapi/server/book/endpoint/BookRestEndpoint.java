package com.cezarykluczynski.stapi.server.book.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.reader.BookRestReader;
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
@Path("v1/rest/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookRestEndpoint {

	private BookRestReader bookRestReader;

	@Inject
	public BookRestEndpoint(BookRestReader bookRestReader) {
		this.bookRestReader = bookRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public BookFullResponse getBook(@QueryParam("uid") String uid) {
		return bookRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public BookBaseResponse searchBook(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return bookRestReader.readBase(BookRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public BookBaseResponse searchBook(@BeanParam BookRestBeanParams bookRestBeanParams) {
		return bookRestReader.readBase(bookRestBeanParams);
	}

}
