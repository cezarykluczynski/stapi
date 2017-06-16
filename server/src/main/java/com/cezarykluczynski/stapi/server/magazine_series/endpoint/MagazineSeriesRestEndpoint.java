package com.cezarykluczynski.stapi.server.magazine_series.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine_series.reader.MagazineSeriesRestReader;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(MediaType.APPLICATION_JSON)
public class MagazineSeriesRestEndpoint {

	public static final String ADDRESS = "/v1/rest/magazineSeries";

	private final MagazineSeriesRestReader magazineSeriesRestReader;

	@Inject
	public MagazineSeriesRestEndpoint(MagazineSeriesRestReader magazineSeriesRestReader) {
		this.magazineSeriesRestReader = magazineSeriesRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineSeriesFullResponse getMagazineSeries(@QueryParam("uid") String uid) {
		return magazineSeriesRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MagazineSeriesBaseResponse searchMagazineSeries(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return magazineSeriesRestReader.readBase(MagazineSeriesRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MagazineSeriesBaseResponse searchMagazineSeries(@BeanParam MagazineSeriesRestBeanParams magazineSeriesRestBeanParams) {
		return magazineSeriesRestReader.readBase(magazineSeriesRestBeanParams);
	}

}
