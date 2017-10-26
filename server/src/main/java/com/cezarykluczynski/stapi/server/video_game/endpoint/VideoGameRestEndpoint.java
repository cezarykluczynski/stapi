package com.cezarykluczynski.stapi.server.video_game.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams;
import com.cezarykluczynski.stapi.server.video_game.reader.VideoGameRestReader;
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
