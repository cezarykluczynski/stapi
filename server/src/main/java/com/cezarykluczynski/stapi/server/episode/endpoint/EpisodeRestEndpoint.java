package com.cezarykluczynski.stapi.server.episode.endpoint;


import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader;
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
public class EpisodeRestEndpoint {

	public static final String ADDRESS = "/v1/rest/episode";

	private final EpisodeRestReader episodeRestReader;

	public EpisodeRestEndpoint(EpisodeRestReader episodeRestReader) {
		this.episodeRestReader = episodeRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public EpisodeFullResponse getEpisode(@QueryParam("uid") String uid) {
		return episodeRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public EpisodeBaseResponse searchEpisode(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return episodeRestReader.readBase(EpisodeRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public EpisodeBaseResponse searchEpisode(@BeanParam EpisodeRestBeanParams episodeRestBeanParams) {
		return episodeRestReader.readBase(episodeRestBeanParams);
	}

}
