package com.cezarykluczynski.stapi.server.book_collection.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.book_collection.reader.BookCollectionRestReader;
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
public class BookCollectionRestEndpoint {

	public static final String ADDRESS = "/v1/rest/bookCollection";

	private final BookCollectionRestReader bookCollectionRestReader;

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
