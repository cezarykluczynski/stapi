package com.cezarykluczynski.stapi.server.comic_collection.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2FullResponse;
import com.cezarykluczynski.stapi.server.comic_collection.reader.ComicCollectionV2RestReader;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class ComicCollectionV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/comicCollection";

	private final ComicCollectionV2RestReader comicCollectionV2RestReader;

	public ComicCollectionV2RestEndpoint(ComicCollectionV2RestReader comicCollectionV2RestReader) {
		this.comicCollectionV2RestReader = comicCollectionV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public ComicCollectionV2FullResponse getComicCollection(@QueryParam("uid") String uid) {
		return comicCollectionV2RestReader.readFull(uid);
	}

}
