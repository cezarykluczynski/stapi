package com.cezarykluczynski.stapi.server.food.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import com.cezarykluczynski.stapi.server.food.reader.FoodRestReader;
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
public class FoodRestEndpoint {

	public static final String ADDRESS = "/v1/rest/food";

	private final FoodRestReader foodRestReader;

	@Inject
	public FoodRestEndpoint(FoodRestReader foodRestReader) {
		this.foodRestReader = foodRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public FoodFullResponse getFood(@QueryParam("uid") String uid) {
		return foodRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public FoodBaseResponse searchCompanies(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return foodRestReader.readBase(FoodRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public FoodBaseResponse searchCompanies(@BeanParam FoodRestBeanParams seriesRestBeanParams) {
		return foodRestReader.readBase(seriesRestBeanParams);
	}

}
