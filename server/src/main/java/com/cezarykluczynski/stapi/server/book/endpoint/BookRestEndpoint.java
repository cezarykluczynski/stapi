package com.cezarykluczynski.stapi.server.book.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.reader.BookRestReader;
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
