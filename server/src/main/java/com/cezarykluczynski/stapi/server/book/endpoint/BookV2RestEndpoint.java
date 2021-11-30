package com.cezarykluczynski.stapi.server.book.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse;
import com.cezarykluczynski.stapi.server.book.dto.BookV2RestBeanParams;
import com.cezarykluczynski.stapi.server.book.reader.BookV2RestReader;
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
public class BookV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/book";

	private final BookV2RestReader bookV2RestReader;

	public BookV2RestEndpoint(BookV2RestReader bookV2RestReader) {
		this.bookV2RestReader = bookV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public BookV2FullResponse getBook(@QueryParam("uid") String uid) {
		return bookV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public BookV2BaseResponse searchBook(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return bookV2RestReader.readBase(BookV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public BookV2BaseResponse searchBook(@BeanParam BookV2RestBeanParams bookRestBeanParams) {
		return bookV2RestReader.readBase(bookRestBeanParams);
	}

}
