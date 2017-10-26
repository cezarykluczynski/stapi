package com.cezarykluczynski.stapi.server.video_release.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseRestReader;
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
public class VideoReleaseRestEndpoint {

	public static final String ADDRESS = "/v1/rest/videoRelease";

	private final VideoReleaseRestReader videoReleaseRestReader;

	public VideoReleaseRestEndpoint(VideoReleaseRestReader videoReleaseRestReader) {
		this.videoReleaseRestReader = videoReleaseRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoReleaseFullResponse getVideoRelease(@QueryParam("uid") String uid) {
		return videoReleaseRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoReleaseBaseResponse searchVideoRelease(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return videoReleaseRestReader.readBase(VideoReleaseRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public VideoReleaseBaseResponse searchVideoRelease(@BeanParam VideoReleaseRestBeanParams videoReleaseRestBeanParams) {
		return videoReleaseRestReader.readBase(videoReleaseRestBeanParams);
	}

}
