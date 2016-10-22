package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.Series;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Path("v1/rest/series")
@Produces(MediaType.APPLICATION_JSON)
public class SeriesRestEndpoint {

	private SeriesRestReader seriesRestReader;

	@Inject
	public SeriesRestEndpoint(SeriesRestReader seriesRestReader) {
		this.seriesRestReader = seriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Series> getSeries() {
		return seriesRestReader.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Series> searchSeries(@BeanParam SeriesRestBeanParams seriesRestBeanParams) {
		return seriesRestReader.search(seriesRestBeanParams);
	}

}
