package com.cezarykluczynski.stapi.server.episode.endpoint;


import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/episode")
@Produces(MediaType.APPLICATION_JSON)
public class EpisodeRestEndpoint {

	private EpisodeRestReader episodeRestReader;

	@Inject
	public EpisodeRestEndpoint(EpisodeRestReader episodeRestReader) {
		this.episodeRestReader = episodeRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public EpisodeResponse getEpisodes(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return episodeRestReader.read(EpisodeRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public EpisodeResponse searchEpisodes(@BeanParam EpisodeRestBeanParams seriesRestBeanParams) {
		return episodeRestReader.read(seriesRestBeanParams);
	}

}
