package com.cezarykluczynski.stapi.server.book.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.reader.BookRestReader;
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
public class BookRestEndpoint {

	public static final String ADDRESS = "/v1/rest/book";

	private final BookRestReader bookRestReader;

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
