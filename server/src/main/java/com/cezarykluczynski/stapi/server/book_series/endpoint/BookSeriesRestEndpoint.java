package com.cezarykluczynski.stapi.server.book_series.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.book_series.reader.BookSeriesRestReader;
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
public class BookSeriesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/bookSeries";

	private final BookSeriesRestReader bookSeriesRestReader;

	public BookSeriesRestEndpoint(BookSeriesRestReader bookSeriesRestReader) {
		this.bookSeriesRestReader = bookSeriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public BookSeriesFullResponse getBookSeries(@QueryParam("uid") String uid) {
		return bookSeriesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public BookSeriesBaseResponse searchBookSeries(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return bookSeriesRestReader.readBase(BookSeriesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public BookSeriesBaseResponse searchBookSeries(@BeanParam BookSeriesRestBeanParams bookSeriesRestBeanParams) {
		return bookSeriesRestReader.readBase(bookSeriesRestBeanParams);
	}

}
