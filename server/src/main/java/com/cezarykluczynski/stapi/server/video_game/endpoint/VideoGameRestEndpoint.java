package com.cezarykluczynski.stapi.server.video_game.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams;
import com.cezarykluczynski.stapi.server.video_game.reader.VideoGameRestReader;
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
public class VideoGameRestEndpoint {

	public static final String ADDRESS = "/v1/rest/videoGame";

	private final VideoGameRestReader videoGameRestReader;

	public VideoGameRestEndpoint(VideoGameRestReader videoGameRestReader) {
		this.videoGameRestReader = videoGameRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoGameFullResponse getVideoGame(@QueryParam("uid") String uid) {
		return videoGameRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoGameBaseResponse searchVideoGame(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return videoGameRestReader.readBase(VideoGameRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public VideoGameBaseResponse searchVideoGame(@BeanParam VideoGameRestBeanParams videoGameRestBeanParams) {
		return videoGameRestReader.readBase(videoGameRestBeanParams);
	}

}
